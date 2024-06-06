package Model.Expressions;

import Model.Utils.Coordinate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BiOperatorExpression extends AbstractExpression {
    private static final List<String> OPERATORS = Arrays.asList("+", "-", "*", "/", "<", ">", "=", "<>", "&", "|", ":");
    String operator;
    ITerm left;
    ITerm right;
    ResultType resultType;

    public BiOperatorExpression(String operator, ITerm left, ITerm right) {
        if (OPERATORS.contains(operator)) {
            this.operator = operator;
        } else {
            throw new IllegalArgumentException("invalid operator");
        }
        this.left = left;
        this.right = right;
        recalculate();
    }

    @Override
    public List<Coordinate> references() {
         List<Coordinate> refs = new ArrayList<>(left.references());
         refs.addAll(right.references());
         return refs;
    }

    @Override
    public ResultType resultType() {
        return resultType;
    }

    /**
     * An operator expression is never considered empty
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void recalculate() {
        left.recalculate();
        right.recalculate();
        // if either side is an error this should mimic that error
        if (left.resultType() == ResultType.error) {
            resultType = ResultType.error;
            value = left.getResult();
            return;
        } else if (right.resultType() == ResultType.error) {
            resultType = ResultType.error;
            value = right.getResult();
            return;
        }
        boolean leftNumber = false;
        boolean rightNumber = false;
        double leftValue = 0;
        double rightValue = 0;
        try {
            leftValue = Double.parseDouble(left.getResult());
            leftNumber = true;
        } catch (NumberFormatException ignored) {}
        try {
            rightValue = Double.parseDouble(right.getResult());
            rightNumber = true;
        } catch (NumberFormatException ignored) {}
        boolean bothNumbers = leftNumber && rightNumber;
        boolean bothStrings = !leftNumber && !rightNumber;

        // TODO: lift error default

        switch (operator) {
            case "+" -> {
                calculateAddition(bothNumbers, leftValue, rightValue);
            }
            case "-" -> {
                calculateMinus(bothNumbers, leftValue, rightValue);
            }
            case "*" -> {
                calculateTimes(bothNumbers, leftValue, rightValue);
            }
            case "/" -> {
                calculateDivision(bothNumbers, rightValue, leftValue);
            }
            case "<" -> {
                calculateLess(bothNumbers, leftValue, rightValue);
            }
            case ">" -> {
                calculateGreater(bothNumbers, leftValue, rightValue);
            }
            case "=" -> {
                calculateEquals(bothNumbers, leftValue, rightValue, bothStrings);
            }
            case "<>" -> {
                calculateNeq(bothNumbers, leftValue, rightValue, bothStrings);
            }
            case "&" -> {
                calculateAnd(bothNumbers, leftValue, rightValue);
            }
            case "|" -> {
                calculateOr(bothNumbers, leftValue, rightValue);
            }
            case ":" -> {
                resultType = ResultType.error;
                value = VALUE_ERROR;
            }
        }
    }

    @Override
    public boolean dependsOn(Coordinate cellLoc) {
        return left.dependsOn(cellLoc) || right.dependsOn(cellLoc);
    }

    private void calculateAddition(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = format(leftValue + rightValue);
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    private void calculateMinus(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = format(leftValue - rightValue);
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    private void calculateTimes(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = format(leftValue * rightValue);
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    private void calculateDivision(boolean bothNumbers, double rightValue, double leftValue) {
        if (bothNumbers) {
            if (rightValue != 0) {
                resultType = ResultType.number;
                value = format(leftValue / rightValue);
            } else {
                resultType = ResultType.error;
                value = DIV_ZERO;
            }
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    private void calculateLess(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = leftValue < rightValue ? "1" : "0";
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    private void calculateGreater(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = leftValue > rightValue ? "1" : "0";
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    private void calculateEquals(boolean bothNumbers, double leftValue, double rightValue,
        boolean bothStrings) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = leftValue == rightValue ? "1" : "0";
        } else if (bothStrings) {
            resultType = ResultType.number;
            value = left.getResult().equals(right.getResult()) ? "1" : "0";
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    private void calculateNeq(boolean bothNumbers, double leftValue, double rightValue,
        boolean bothStrings) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = leftValue != rightValue ? "1" : "0";
        } else if (bothStrings) {
            resultType = ResultType.number;
            value = !left.getResult().equals(right.getResult()) ? "1" : "0";
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    private void calculateAnd(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = Math.abs(leftValue - 0) > 1e-9 && Math.abs(rightValue - 0) > 1e-9 ? "1" : "0";
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    private void calculateOr(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = Math.abs(leftValue - 0) > 1e-9 || Math.abs(rightValue - 0) > 1e-9 ? "1" : "0";
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }
}
