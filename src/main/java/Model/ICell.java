package Model;

import Model.Utils.IRef;
import Model.Utils.ITerm;

/**
 * An ICell is a rectangular cell in a spreadsheet with a term of data
 * and formatting details
 * Jackson Magas
 */
public interface ICell extends ICellListener {
    // Change the contents of the cell to the given data and call
    void updateCell(ITerm data);

    // get a reference to this cell
    IRef getRef();

    // returns the data contained in this cell
    ITerm getData();

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
}
