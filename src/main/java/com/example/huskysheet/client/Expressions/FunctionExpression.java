package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Utils.Conversions;
import com.example.huskysheet.client.Utils.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Represents an expression that is a function of some list of arguments, including range args.
 * @author Jackson Magas
 */
public class FunctionExpression extends AbstractExpression {
    private final List<ITerm> args = new ArrayList<>();
    private final FunctionType type;
    private ResultType resultType;

    public enum FunctionType {
        IF, SUM, MAX, MIN, AVG, CONCAT, DEBUG, COPY
    }

    /**
     * Create a new function object from a function and its arguments
     * @author Jackson Magas
     * @param type the enum type of the function
     * @param parameters The terms to use as arguments for the function
     *                   RangeExpressions can be provided as a parameter
     */
    public FunctionExpression(FunctionType type, List<ITerm> parameters) {
        super(buildPlaintext(type, parameters));
        this.type = type;
        for (ITerm arg : parameters) {
            // Coupling is annoying but would need a major refactor to fix
            if (arg.resultType() == ResultType.range) {
                args.addAll(arg.getMultipleResults());
            } else {
                args.add(arg);
            }
        }
        recalculate();
    }

    /**
     * Build up the plaintext of this function from the plaintext of the args
     * @param type the function type
     * @param parameters the arguments to the function
     * @return the plaintext string
     */
    private static String buildPlaintext(FunctionType type, List<ITerm> parameters) {
        StringBuilder plaintext = new StringBuilder().append(type).append("(");
        for (ITerm arg : parameters) {
            plaintext.append(arg.toString()).append(", ");
        }
        if (plaintext.charAt(plaintext.length() - 2) == ',') {
            plaintext.delete(plaintext.length() - 2, plaintext.length());
        }
        return plaintext.append(")").toString();
    }

    @Override
    public List<Coordinate> references() {
        return args.stream().map(ITerm::references).flatMap(List::stream).toList();
    }

    @Override
    public ResultType resultType() {
        return resultType;
    }

    /**
     * A function is never considered empty
     * @return false
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Recalculate the value of this function based on its dependencies and function type
     * @author Jackson Magas
     */
    @Override
    public void recalculate() {
        List<String> emptyRemoved = new ArrayList<>();
        for (ITerm arg : args) {
            arg.recalculate();
            if (arg.resultType() != ResultType.empty) {
                emptyRemoved.add(arg.getResult());
            }
            // if an argument is an error this is that error too
            if (arg.resultType() == ResultType.error) {
                resultType = ResultType.error;
                value = arg.getResult();
                return;
            }
        }
        switch (type) {
            case IF -> {
                // if does not ignore empty
                value = calculateIf();
            }
            case SUM -> {
                value = calculateSum(emptyRemoved);
            }
            case MAX -> {
                value = calculateMax(emptyRemoved);
            }
            case MIN -> {
                value = calculateMin(emptyRemoved);
            }
            case AVG -> {
                value = calculateAvg(emptyRemoved);
            }
            case CONCAT -> {
                value = calculateConcat(emptyRemoved);
            }
            case DEBUG -> {
                // debug does not ignore empty
                value = calculateDebug();
            }
            case COPY -> {
                value = calculateCopy();
            }
        }
    }

    /**
     * Set the value of this term to the left argument and update the cell referenced by the right
     * argument to the value of the left argument. Errors if there are not 2 arguments with the right
     * side being a reference as a string
     * @return the value of the left arg
     */
    private String calculateCopy() {
        if (args.size() == 2
            && (args.getFirst() instanceof ReferenceExpression)
            && (args.getLast().resultType() == ResultType.string)
            && Conversions.isValidRef(args.getLast().getResult())) {
            // the implementation of expressions which don't know what spreadsheet they are part of
            // died here (it probably wasn't the best idea anyway)
            ((ReferenceExpression) args.getFirst()).getSpreadsheet()
                .getCell(Conversions.stringToCoordinate(args.getLast().getResult()))
                .updateCell("="+args.getFirst().toString());
            resultType = args.getFirst().resultType();
            return args.getFirst().getResult();
        } else {
            resultType = ResultType.error;
            return VALUE_ERROR;
        }
    }

    @Override
    public boolean dependsOn(Coordinate cellLoc) {
        return args.stream().anyMatch(arg -> arg.dependsOn(cellLoc));
    }

    /**
     * Causes this expression to mirror its argument and print it to stdout
     * errors if there is not exactly one argument
     * @author Jackson Magas
     * @return the result of the argument
     */
    private String calculateDebug() {
        if (args.size() != 1) {
            resultType = ResultType.error;
            return VALUE_ERROR;
        }

        resultType = args.getFirst().resultType();
        String result = args.getFirst().getResult();
        System.out.println(result);
        return result;
    }

    /**
     * Concatenate string arguments
     * @author Jackson Magas
     * @param args the strings to concatenate
     * @return the result of concatenating the arguments
     */
    private String calculateConcat(List<String> args) {
        for (String arg : args) {
            if (isNumber(arg)) {
                resultType = ResultType.error;
                return VALUE_ERROR;
            }
        }

        resultType = ResultType.string;
        return args.stream().reduce("", (a, b) -> a + b);
    }

    /**
     * Get the average value of the arguments
     * If any arguments are not numbers or if there are no arguments return VALUE_ERROR
     * @author Jackson Magas
     * @param args the arguments to average
     * @return AVG(args) or VALUE_ERROR
     */
    private String calculateAvg(List<String> args) {
        for (String arg : args) {
            if (!isNumber(arg)) {
                resultType = ResultType.error;
                return VALUE_ERROR;
            }
        }

        OptionalDouble result = args.stream().mapToDouble(Double::parseDouble).average();
        if (result.isPresent()) {
            resultType = ResultType.number;
            return format(result.getAsDouble());
        } else {
            resultType = ResultType.error;
            return VALUE_ERROR;
        }
    }

    /**
     * Get the min value of the arguments.
     * If any arguments are not numbers, or if there are no arguments return value error
     * @author Jackson Magas
     * @param args stringified arguments to use
     * @return MIN(args) or VALUE_ERROR
     */
    private String calculateMin(List<String> args) {
        for (String arg : args) {
            if (!isNumber(arg)) {
                resultType = ResultType.error;
                return VALUE_ERROR;
            }
        }

        OptionalDouble result = args.stream().mapToDouble(Double::parseDouble).min();
        if (result.isPresent()) {
            resultType = ResultType.number;
            return format(result.getAsDouble());
        } else {
            resultType = ResultType.error;
            return VALUE_ERROR;
        }
    }

    /**
     * Get the max value of the arguments.
     * If any arguments are not numbers, or if there are no arguments return value error
     * @author Jackson Magas
     * @param args stringified arguments to use
     * @return MAX(args) or VALUE_ERROR
     */
    private String calculateMax(List<String> args) {
        for (String arg : args) {
            if (!isNumber(arg)) {
                resultType = ResultType.error;
                return VALUE_ERROR;
            }
        }

        OptionalDouble result = args.stream().mapToDouble(Double::parseDouble).max();
        if (result.isPresent()) {
            resultType = ResultType.number;
            return format(result.getAsDouble());
        } else {
            resultType = ResultType.error;
            return VALUE_ERROR;
        }
    }

    /**
     * Calculate the sum of the arguments to this function.
     * @author Jackson Magas
     * @return the sum of the args or VALUE_ERROR if they are not all numbers
     */
    private String calculateSum(List<String> args) {
       for (String arg : args) {
           if (!isNumber(arg)) {
               resultType = ResultType.error;
               return VALUE_ERROR;
           }
       }

       resultType = ResultType.number;
       return format(args.stream().mapToDouble(Double::parseDouble).sum());
    }

    /**
     * check if the string is a number
     * @author Jackson Magas
     * @param s the potential number to check
     * @return true if the string can be parsed to a double
     */
    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Set the value of this function to the value of IF on its args
     * IF needs 3 args with the first being a number
     * @author Jackson Magas
     * @return the value of IF(arg[1], arg[2], arg[3])
     */
    private String calculateIf() {
        if (args.size() == 3 && isNumber(args.getFirst().getResult())) {
            if (args.getFirst().getResult().equals("0")) {
                resultType = args.get(2).resultType();
                return args.get(2).getResult();
            } else {
                resultType = args.get(1).resultType();
                return args.get(1).getResult();
            }
        } else {
            resultType = ResultType.error;
            return VALUE_ERROR;
        }
    }
}
