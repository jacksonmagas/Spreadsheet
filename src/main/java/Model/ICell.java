package Model;

import Model.Utils.Coordinate;
import Model.Expressions.ITerm.ResultType;

/**
 * An ICell is a rectangular cell in a spreadsheet with a term of data
 * and formatting details
 * Jackson Magas
 */
public interface ICell extends ICellValueListener {
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
    CellFormat getFormatting();

    // returns true if the cell is empty
    boolean isEmpty();

    // Recalculates the value of the cell in response to a cell
    // this depends on changes
    void handleValueChange();

    // Register as a listener for changes to the value of this cell including changes to underlying
    // referenced cells
    void registerValueListener(ICellValueListener listener);

    // Register as a listener for direct changes to the contents of this cell
    void registerUpdateListener(ICellUpdateListener listener);

    // Notify all references to this cell that the cell has changed
    void notifyValueListeners();

    // Notify listeners that this cell has been directly changed
    void notifyUpdateListeners();

    /**
     * Set the formatting of the cell to the new formatting
     * @param formatting formatting
     */
    void setFormatting(CellFormat formatting);

    /**
     * Get the type of data held by this cell either string, number, or empty
     * @return the data type
     */
    ResultType dataType();

}
