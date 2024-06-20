package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Utils.Coordinate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for an expression which is a binomial operator applied to two other terms.
 * @author Jackson Magas
 *
 */
public class BiOperatorExpression extends AbstractExpression {
    private static final List<String> OPERATORS = Arrays.asList("+", "-", "*", "/", "<", ">", "=", "<>", "&", "|", ":");
    String operator;
    ITerm left;
    ITerm right;
    ResultType resultType;

    /**
     * Create a new expression from the operator and the two terms.
     * @param operator one of "+", "-", "*", "/", "<", ">", "=", "<>", "&", "|", ":"
     * @param left the left operand term
     * @param right the right operand term
     * @author Jackson Magas
     * @throws IllegalArgumentException if the operator is not from that list
     */
    public BiOperatorExpression(String operator, ITerm left, ITerm right) {
        super(left.toString() + " " + operator + " " + right.toString());
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
     * @author Jackson Magas
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Recalculate the value of this expression by recalculating both operands and then calculating
     * the correct operation.
     * @author Jackson Magas
     */
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

    /**
     * Set the value of this term to the sum of the adjacent terms if both numbers, otherwise set it
     * to {@value VALUE_ERROR}
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @author Jackson Magas
     */
    private void calculateAddition(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = format(leftValue + rightValue);
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }


    /**
     * Set the value of this term to the difference of the adjacent terms if both numbers, otherwise set it
     * to {@value VALUE_ERROR}
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @author Jackson Magas
     */
    private void calculateMinus(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = format(leftValue - rightValue);
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }


    /**
     * Set the value of this term to the product of the adjacent terms if both numbers, otherwise set it
     * to {@value VALUE_ERROR}
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @author Jackson Magas
     */
    private void calculateTimes(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = format(leftValue * rightValue);
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    /**
     * Set the value of this term to the quotient of the adjacent terms if both numbers, otherwise set it
     * to {@value VALUE_ERROR}, or if the divisor is 0 to {@value DIV_ZERO}.
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @author Jackson Magas
     */
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

    /**
     * Set the value of this term to 1 if left < right otherwise 0, if either is not a number set it
     * to {@value VALUE_ERROR}
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @author Jackson Magas
     */
    private void calculateLess(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = leftValue < rightValue ? "1" : "0";
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    /**
     * Set the value of this term to 1 if left > right otherwise 0, if either is not a number set it
     * to {@value VALUE_ERROR}
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @author Jackson Magas
     */
    private void calculateGreater(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = leftValue > rightValue ? "1" : "0";
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    /**
     * Set value to 1 if the terms are both equal numbers or both equal strings, 0 if they have
     * the same type and are not equal, and {@value VALUE_ERROR} if they have different types
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @param bothStrings are both args strings?
     * @author Jackson Magas
     */
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

    /**
     * Set value to 0 if the terms are both equal numbers or both equal strings, 1 if they have
     * the same type and are not equal, and {@value VALUE_ERROR} if they have different types
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @param bothStrings are both args strings?
     * @author Jackson Magas
     */
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

    /**
     * Set the value of this term to 1 if left != 0 && right != 0, if either is not a number set it
     * to {@value VALUE_ERROR}
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @author Jackson Magas
     */
    private void calculateAnd(boolean bothNumbers, double leftValue, double rightValue) {
        if (bothNumbers) {
            resultType = ResultType.number;
            value = Math.abs(leftValue - 0) > 1e-9 && Math.abs(rightValue - 0) > 1e-9 ? "1" : "0";
        } else {
            resultType = ResultType.error;
            value = VALUE_ERROR;
        }
    }

    /**
     * Set the value of this term to 1 if left != 0 || right != 0, if either is not a number set it
     * to {@value VALUE_ERROR}
     * @param bothNumbers are both args numbers?
     * @param leftValue the numeric value of left arg
     * @param rightValue the numeric value of right arg
     * @author Jackson Magas
     */
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
