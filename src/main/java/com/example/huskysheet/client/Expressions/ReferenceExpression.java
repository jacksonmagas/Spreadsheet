package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Model.ICell;
import com.example.huskysheet.client.Model.ISpreadsheet;
import com.example.huskysheet.client.Utils.Coordinate;
import java.util.List;
import java.util.Objects;

/**
 * Represents a term in an expression which is a reference to the value of a cell.
 * @author Jackson Magas
 */
public class ReferenceExpression extends AbstractExpression {
    ICell reference;

    /**
     * Create a new reference expression to the given cell
     * @param cell the cell to reference
     * @author Jackson Magas
     */
    public ReferenceExpression(ICell cell) {
        super(cell.getCoordinate().toString());
        reference = cell;
        plaintext = cell.getCoordinate().toString();
        recalculate();
    }

    /**
     * Get the spreadsheet this cell is referencing, increases coupling but is required
     * for the copy function.
     * @return the sheet the referenced cell belongs to
     * @author Jackson Magas
     */
    protected ISpreadsheet getSpreadsheet() {
        return reference.getSpreadsheet();
    }

    /**
     * A reference expression directly references one cell
     * @return the coordinate of the referenced cell
     * @author Jackson Magas
     */
    @Override
    public List<Coordinate> references() {
        return List.of(reference.getCoordinate());
    }

    /**
     * The type of the result of evaluating this is the type of evaluating the referenced cell
     * @return the result type of the underlying cell
     * @author Jackson Magas
     */
    @Override
    public ResultType resultType() {
        return reference.dataType();
    }

    /**
     * This reference is empty if the referenced cell was empty
     * @return true if the underlying cell is empty
     * @author Jackson Magas
     */
    @Override
    public boolean isEmpty() {
        return reference.isEmpty();
    }

    /**
     * Update the cached value based on the value of the underlying reference
     * @author Jackson Magas
     */
    @Override
    public void recalculate() {
        value = reference.getData();
    }

    /**
     * Determine if this reference depends on the given cell.
     * @param cellLoc the location of the cell to check for
     * @return true if this reference is the given cell or the referenced cell depends on this cell
     * @author Jackson Magas
     */
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
