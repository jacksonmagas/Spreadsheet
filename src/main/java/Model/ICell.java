package Model;

import Model.Utils.IRef;
import Model.Utils.ITerm;

/**
 * An ICell is a rectangular cell in a spreadsheet with a term of data
 * and formatting details
 * Jackson Magas
 */
public interface ICell {
    // Change the contents of the cell to the given data and call
    boolean updateCell(ITerm data);

    // get a reference to this cell
    IRef getRef();

    // returns the data contained in this cell
    ITerm getData();

    // returns an object containing details about the formatting of the cell
    CellFormat getFormatting();

    // returns true if the cell is empty
    boolean isEmpty();

    // Recalculates the value of the cell (only really relavent for functions)
    void recalculateCell();

    // Register as a listener for changes to this cell
    void registerListener(ICellListener listener);

    // Notify all references to this cell that the cell has changed
    void notifyListeners();
}
