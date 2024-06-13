package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Model.ICell;
import com.example.huskysheet.client.Utils.Coordinate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Represents a range of cells.
 * Trying to get the value of a range is a value error, ranges are instead used for functions
 * @author Jackson Magas
 */
public class RangeExpression extends AbstractExpression {
    List<ICell> cells;

    public RangeExpression(List<ICell> cells) throws IllegalArgumentException {
        super(buildPlaintext(cells));
        this.cells = cells;
        value = VALUE_ERROR;
    }

    private static String buildPlaintext(List<ICell> cells) {
        try {
            return cells.getFirst().getCoordinate().toString()
                + ":"
                + cells.getLast().getCoordinate().toString();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns an unmodifiable list of reference expressions for all of the cells in this range
     * @author Jackson Magas
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
