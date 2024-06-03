package Model.Utils;

import java.util.ArrayList;
import java.util.List;

public class FunctionExpression extends AbstractExpression {
    @Override
    public void recalculate() {
        //TODO implement
    }

    private final List<ITerm> args = new ArrayList<>();
    private final FunctionType type;

    public enum FunctionType {
        IF, SUM, MAX, MIN, AVG, CONCAT, DEBUG
    }

    /**
     * Create a new function object from a function and its arguments
     * @param type the enum type of the function
     * @param parameters The terms to use as arguments for the function
     *                   RangeExpressions can be provided as a parameter
     */
    public FunctionExpression(FunctionType type, List<ITerm> parameters) {
        this.type = type;
        for (ITerm arg : parameters) {
            // Coupling is annoying but would need a major refactor to fix
            if (arg instanceof RangeExpression) {
                args.addAll(((RangeExpression) arg).getReferenceExpressions());
            }

        }
    }
}
