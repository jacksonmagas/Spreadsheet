package Model;

import Model.Utils.Coordinate;

/**
 * Interface for classes which listen for changes to a spreadsheet
 */
public interface ISpreadsheetListener {
    /**
     * Update the listener based on the new spreadsheet update
     */
    void handleUpdate(Coordinate coordinate, String update) throws Exception;
}
