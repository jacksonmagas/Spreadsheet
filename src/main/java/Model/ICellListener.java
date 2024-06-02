package Model;

import Model.Utils.Coordinate;

public interface ICellListener {
    /**
     * Update the listener based on the new value of a referenced cell
     *
     */
    void handleUpdate();

    /**
     * Detect a circular reference in the listener chain
     * @param source the Coordinate of the source cell
     * @return true if this cell or any of its listeners
     * have the source as a listener
     */
    boolean circularReference(ICell source);
}
