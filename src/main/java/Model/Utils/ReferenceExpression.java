package Model.Utils;

import Model.ICell;
import java.util.List;
import java.util.Objects;

public class ReferenceExpression extends Expression {
    ICell reference;

    public ReferenceExpression(ICell cell) {
        reference = cell;
        plaintext = cell.getCoordinate().toString();
    }

    @Override
    public List<Coordinate> dependencies() {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void recalculate() {
        if (reference.getData() != null) {
            value = reference.getData().getResult();
        } else {
            value = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ReferenceExpression && ((ReferenceExpression) o).reference.equals(reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }
}
