package Model;

import Model.Utils.Coordinate;

/**
 * Interface for classes which listen for direct changes to the contents of a cell, ignoring value
 * changes
 */
public interface ICellUpdateListener {
    /**
     * Update the listener based on the new value of a referenced cell
     */
    void handleUpdate();
}
