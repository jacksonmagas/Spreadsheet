package Model;

/**
 * Interface for classes which listen for changes to a spreadsheet
 */
public interface ISpreadsheetListener {
    /**
     * Update the listener based on the new spreadsheet update
     */
    void handleUpdate(String update);
}
