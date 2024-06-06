package Model.Expressions;

import Model.Utils.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Represents an expression that is a function of some list of arguments, including range args.
 */
public class FunctionExpression extends AbstractExpression {
    private final List<ITerm> args = new ArrayList<>();
    private final FunctionType type;
    private ResultType resultType;

    public enum FunctionType {
        IF, SUM, MAX, MIN, AVG, CONCAT, DEBUG
    }

    /**
     * Create a new function object from a function and its arguments
     * Jackson Magas
     * @param type the enum type of the function
     * @param parameters The terms to use as arguments for the function
     *                   RangeExpressions can be provided as a parameter
     */
    public FunctionExpression(FunctionType type, List<ITerm> parameters) {
        this.type = type;
        StringBuilder plaintext = new StringBuilder().append(type).append("(");
        for (ITerm arg : parameters) {
            plaintext.append(arg.toString()).append(", ");
            // Coupling is annoying but would need a major refactor to fix
            if (arg instanceof RangeExpression) {
                args.addAll(((RangeExpression) arg).getReferenceExpressions());
            } else {
                args.add(arg);
            }
        }
        if (!args.isEmpty()) {
            plaintext.setLength(plaintext.length() - 1);
        }
        this.plaintext = plaintext.append(")").toString();
        recalculate();
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
     * Jackson Magas
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
        }
    }

    @Override
    public boolean dependsOn(Coordinate cellLoc) {
        return args.stream().anyMatch(arg -> arg.dependsOn(cellLoc));
    }

    /**
     * Causes this expression to mirror its argument and print it to stdout
     * errors if there is not exactly one argument
     * Jackson Magas
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
     * Jackson Magas
     * @param args
     * @return
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
     * Jackson Magas
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
     * Jackson Magas
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
     * Jackson Magas
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
     * Jackson Magas
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
     * Jackson Magas
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
     * Jackson Magas
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
