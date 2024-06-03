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
    public void recalculate() {

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
