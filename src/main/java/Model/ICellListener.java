package Model;

import Model.Utils.Coordinate;

public interface ICellListener {

    /**
     * Update the listener based on the new value of a referenced cell
     */
    void handleValueChange();

    /**
     * Determine whether the value of this cellValueListener depends on the value of the given cell
     * @param cellLoc the cell to search the dependency tree for
     * @return true if this cell depends on the given cell
     */
    boolean dependsOn(Coordinate cellLoc);
}
