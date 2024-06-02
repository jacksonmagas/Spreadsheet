package Model.Utils;

public class ParenExpression extends Expression {

    ITerm enclosed;

    public ParenExpression(ITerm enclosed) {
        this.enclosed = enclosed;
    }

    @Override
    public void recalculate() {

    }
}
