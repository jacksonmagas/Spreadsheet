package Model.Utils;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class BiOperatorExpression extends AbstractExpression {
    private static final List<String> OPERATORS = Arrays.asList("+", "-", "*", "/", "<", ">", "=", "<>", "&", "|", ":");
    private static final String DIV_ZERO = "#DIV/0!";
    String operator;
    ITerm left;
    ITerm right;

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
    public void recalculate() {
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
               if (bothNumbers) {
                   value = format(leftValue + rightValue);
               } else {
                   value = VALUE_ERROR;
               }
            }
            case "-" -> {
                if (bothNumbers) {
                    value = format(leftValue - rightValue);
                } else {
                    value = VALUE_ERROR;
                }
            }
            case "*" -> {
                if (bothNumbers) {
                    value = format(leftValue * rightValue);
                } else {
                    value = VALUE_ERROR;
                }
            }
            case "/" -> {
                if (bothNumbers) {
                    if (rightValue != 0) {
                        value = format(leftValue / rightValue);
                    } else {
                        value = DIV_ZERO;
                    }
                } else {
                    value = VALUE_ERROR;
                }
            }
            case "<" -> {
                if (bothNumbers) {
                   value = leftValue < rightValue ? "1" : "0";
                } else {
                    value = VALUE_ERROR;
                }
            }
            case ">" -> {
                if (bothNumbers) {
                    value = leftValue > rightValue ? "1" : "0";
                } else {
                    value = VALUE_ERROR;
                }
            }
            case "=" -> {
                if (bothNumbers) {
                    value = leftValue == rightValue ? "1" : "0";
                } else if (bothStrings) {
                    value = left.getResult().equals(right.getResult()) ? "1" : "0";
                } else {
                    value = VALUE_ERROR;
                }
            }
            case "<>" -> {
               if (bothNumbers) {
                   value = leftValue != rightValue ? "1" : "0";
               } else if (bothStrings) {
                   value = !left.getResult().equals(right.getResult()) ? "1" : "0";
               } else {
                   value = VALUE_ERROR;
               }
            }
            case "&" -> {
                if (bothNumbers) {
                    value = Math.abs(leftValue - 0) > 1e-9 && Math.abs(rightValue - 0) > 1e-9 ? "1" : "0";
                } else {
                    value = VALUE_ERROR;
                }
            }
            case "|" -> {
                if (bothNumbers) {
                    value = Math.abs(leftValue - 0) > 1e-9 || Math.abs(rightValue - 0) > 1e-9 ? "1" : "0";
                } else {
                    value = VALUE_ERROR;
                }
            }
            case ":" -> {
                value = VALUE_ERROR;
            }
        }
    }

    private String format(double value) {
        return new DecimalFormat("#.################").format(value);
    }
}
