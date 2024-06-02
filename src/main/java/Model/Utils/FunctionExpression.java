package Model.Utils;

import java.util.List;

public class FunctionExpression extends Expression {
    @Override
    public void recalculate() {

    }

    public enum FunctionType {
        IF, SUM, MAX, MIN, AVG, CONCAT, DEBUG
    }

    FunctionType functionType;

    public FunctionExpression(FunctionType type, List<ITerm> parameters) {
        //TODO
    }
}
