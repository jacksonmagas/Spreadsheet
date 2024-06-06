package com.example.huskysheet.service;

import com.example.huskysheet.utils.Coordinate;

public interface ICellListener {

    /**
     * Update the listener based on the new value of a referenced cell
     */
    void handleUpdate();

    /**
     * Determine whether the value of this cellListener depends on the value of the given cell
     * @param cellLoc the cell to search the dependency tree for
     * @return true if this cell depends on the given cell
     */
    boolean dependsOn(Coordinate cellLoc);
}
