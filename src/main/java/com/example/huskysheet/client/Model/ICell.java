package com.example.huskysheet.client.Model;

import com.example.huskysheet.client.Utils.CellFormatDetails;
import com.example.huskysheet.client.Expressions.ITerm.ResultType;
import com.example.huskysheet.client.Utils.Coordinate;

/**
 * An ICell is a rectangular cell in a spreadsheet with a term of data
 * and formatting details
 * @author Jackson Magas
 */
public interface ICell extends ICellListener {
    // Set the contents of this cell to the given data
    void updateCell(String data);

    // get the coordinates of this cell
    Coordinate getCoordinate();

    /**
     * Get the value of the user input in this cell once parsed and evaluated
     * @return The value of the user input as a string
     */
    String getData();

    /**
     * Get the verbatim text the user entered into this cell
     * @return The user entered text as a string
     */
    String getPlaintext();

    // returns an object containing details about the formatting of the cell
    CellFormatDetails getFormatting();

    // returns true if the cell is empty
    boolean isEmpty();

    // Recalculates the value of the cell in response to a cell
    // this depends on changes
    void handleValueChange();

    // Register as a listener for changes to the value of this cell including changes to underlying
    // referenced cells
    void registerListener(ICellListener listener);

    // Notify all references to this cell that the cell has changed
    void notifyListeners();

    /**
     * Set the formatting of the cell to the new formatting
     * @param formatting formatting
     */

    void setFormatting(CellFormatDetails formatting);

    /**
     * Get the type of data held by this cell either string, number, or empty
     * @return the data type
     */
    ResultType dataType();

    /**
     * Get the spreadsheet this cell belongs to.
     * @return the spreadsheet this cell is a part of
     */
    ISpreadsheet getSpreadsheet();

}
