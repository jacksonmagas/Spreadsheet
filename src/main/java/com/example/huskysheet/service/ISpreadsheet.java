package com.example.huskysheet.service;

import com.example.huskysheet.utils.Coordinate;

import java.util.List;
import javafx.util.Pair;

public interface ISpreadsheet {
    // Return the cell at the given coordinates, creating a new cell object if the cell is empty.
    ICell getCell(Coordinate coordinate);

    // Update the cell at each coordinate to the given string
    void updateSheet(List<Pair<Coordinate, String>> update);


    /**
     * Set a format option for a row or column
     * TODO
     */

    /*
    void setRowColor(int row);

    void setColumnColor(int col);

    void setRowFont(int row);

    void setColumnFont(int col);
     */
}
