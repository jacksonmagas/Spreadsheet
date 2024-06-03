package Server.api;

import Server.model.Spreadsheet;

import java.util.HashMap;
import java.util.Map;

public class UpdateSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;
    private final Map<String, String> cellLocks;

    public UpdateSpreadsheet(SpreadsheetManager spreadsheetManager) {
        this.spreadsheetManager = spreadsheetManager;
        this.cellLocks = new HashMap<>();
    }

    public void editCells(String spreadsheetName, int row, int col, String newValue, int version) {
        Spreadsheet spreadsheet = spreadsheetManager.getSpreadsheet(spreadsheetName);

        if (spreadsheet != null) {
            spreadsheet.setValue(row, col, newValue, version);
            spreadsheetManager.updateSpreadsheet(spreadsheet);
            System.out.println("Cell (" + row + ", " + col + ") has been updated");
        } else {
            System.out.println("Spreadsheet does not exist!");
        }
    }
}
