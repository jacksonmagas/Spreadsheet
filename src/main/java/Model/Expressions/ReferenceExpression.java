package Model.Expressions;

import Model.ICell;
import Model.Utils.Coordinate;
import java.util.List;
import java.util.Objects;

public class ReferenceExpression extends AbstractExpression {
    ICell reference;

    public ReferenceExpression(ICell cell) {
        value = VALUE_ERROR; // Default value overridden when recalculated
        reference = cell;
        plaintext = cell.getCoordinate().toString();
        recalculate();
    }

    @Override
    public List<Coordinate> references() {
        return List.of(reference.getCoordinate());
    }

    @Override
    public ResultType resultType() {
        return reference.dataType();
    }

    @Override
    public boolean isEmpty() {
        return reference.isEmpty();
    }

    @Override
    public void recalculate() {
        value = reference.getData();
    }

    @Override
    public boolean dependsOn(Coordinate cellLoc) {
        return cellLoc.equals(reference.getCoordinate()) || reference.dependsOn(cellLoc);
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
