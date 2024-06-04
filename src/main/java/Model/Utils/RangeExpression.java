package Model.Utils;

import Model.ICell;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a range of cells.
 * Trying to get the value of a range is a value error, ranges are instead used for functions
 * Jackson Magas
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

    // getting the result of a range directly is an error
    @Override
    public ResultType resultType() {
        return ResultType.error;
    }

    @Override
    public boolean isEmpty() {
        return cells.stream().allMatch(ICell::isEmpty);
    }

    @Override
    public void recalculate() {

    }
}
