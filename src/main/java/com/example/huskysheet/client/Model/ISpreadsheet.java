
package com.example.huskysheet.client.Model;

import com.example.huskysheet.client.Utils.Coordinate;

import java.util.List;
import javafx.util.Pair;

/**
 * Interface for spreadsheet data objects.
 */
public interface ISpreadsheet {

    /**
     * Return the cell at the given coordinates, creating a new cell object if the cell is empty.
     */
    ICell getCell(Coordinate coordinate);

    /**
     * Return the number of rows in this spreadsheet
     * @return the row of the furthest cell
     */
    int numRows();

    /**
     * Return the number of columns in this spreadsheet
     * @return the column of the furthest cell
     */
    int numColumns();

    /**
     * Get a view of a row of this spreadsheet as a list
     * @param rowNum the row number
     * @return A list of cells in the row
     */
    List<ICell> getRow(int rowNum);

    /**
     * Get a view of a column of this spreadsheet as a list
     * @param colNum the column number
     * @return A list of cells in that column
     */
    List<ICell> getColumn(int colNum);

    /**
     * Update the cell at each coordinate to the given string
     * @param update A list of coordinates and the new value for that coordinate
     */
    boolean updateSheet(List<Pair<Coordinate, String>> update);

    /**
     * Register a new listener for changes to the data of the spreadsheet
     * @param listener the listener to register
     */
    void registerListener(ISpreadsheetListener listener);

    /**
     * Unregister a given listener if it is registered
     * @param listener the listener to unregister
     */
    void unregisterListener(ISpreadsheetListener listener);

    /**
     * Notify listeners of a change to the spreadsheet
     * @param coordinate the location of the change
     * @param update the text of the change
     */
    void notifyListeners(Coordinate coordinate, String update);
}
