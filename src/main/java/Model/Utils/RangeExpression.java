package Model.Utils;

import Model.ICell;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a range of cells.
 * Jackson Magas
 * //TODO This class violates the Liskov substitution principle but I don't have time to fix it
 */
public class RangeExpression extends AbstractExpression {
    List<ICell> cells;

    public RangeExpression(List<ICell> cells) {
        this.cells = cells;
        this.plaintext = cells.getFirst().getCoordinate().toString()
            + ":"
            + cells.getLast().getCoordinate().toString();
        value = VALUE_ERROR;
    }

    /**
     * Returns an unmodifiable list of reference expressions for all of the cells in this range
     * Jackson Magas
     * @return reference expressions for the cells in this range
     */
    public List<ITerm> getReferenceExpressions() {
        return cells.stream().map(ReferenceExpression::new).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void recalculate() {

    }
}
