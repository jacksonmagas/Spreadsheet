package Model;

import Model.Utils.Coordinate;
import Model.Utils.ITerm;

/**
 * An ICell is a rectangular cell in a spreadsheet with a term of data
 * and formatting details
 * Jackson Magas
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
    CellFormat getFormatting();

    // returns true if the cell is empty
    boolean isEmpty();

    // Recalculates the value of the cell in response to a cell
    // this depends on changes
    void handleUpdate();

    // Register as a listener for changes to this cell
    void registerListener(ICellListener listener);

    // Notify all references to this cell that the cell has changed
    void notifyListeners();

    /**
     * Set the formatting of the cell to the new formatting
     * @param formatting formatting
     */
    void setFormatting(CellFormat formatting);
}
