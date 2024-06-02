package Model.Utils;

import java.util.Arrays;
import java.util.List;

public class BiOperatorExpression extends Expression {
    private static List<String> operators = Arrays.asList("+", "-", "*", "/", "<", ">", "=", "<>", "&", "|", ":");
    String operator;
    ITerm left;
    ITerm right;

    public BiOperatorExpression(String operator, ITerm left, ITerm right) {
        if (operators.contains(operator)) {
            this.operator = operator;
        } else {
            throw new IllegalArgumentException("invalid operator");
        }
        this.left = left;
        this.right = right;
    }

    @Override
    public void recalculate() {

    }
}
