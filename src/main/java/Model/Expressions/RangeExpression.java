package Model.Expressions;

import Model.ICell;
import Model.Utils.Coordinate;
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

    @Override
    public List<Coordinate> references() {
        return getReferenceExpressions().stream().map(ITerm::references).flatMap(List::stream).toList();
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

    @Override
    public boolean dependsOn(Coordinate cellLoc) {
        return cells.stream().anyMatch((ICell c) -> c.dependsOn(cellLoc));
    }
}
