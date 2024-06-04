package Model.Utils;

import java.util.Objects;

public class ParenExpression extends AbstractExpression {

    ITerm enclosed;

    public ParenExpression(ITerm enclosed) {
        this.enclosed = enclosed;
        this.plaintext = "(" + enclosed.toString() + ")";
        this.value = enclosed.getResult();
    }

    @Override
    public ResultType resultType() {
        return enclosed.resultType();
    }

    @Override
    public boolean isEmpty() {
        return enclosed.isEmpty();
    }

    @Override
    public void recalculate() {
        value = enclosed.getResult();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ITerm && o.equals(enclosed);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(enclosed);
    }
}
