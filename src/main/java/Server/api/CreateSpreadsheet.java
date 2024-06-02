package Server.api;

import Server.model.Spreadsheet;

public class CreateSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;

    public CreateSpreadsheet() {
        this.spreadsheetManager = new SpreadsheetManager();
    }

    public Spreadsheet createSpreadsheet(String name, int rows, int cols) {
        if (spreadsheetManager.containsSpreadsheet(name)) {
            throw new IllegalArgumentException("Spreadsheet Name Already Exists");
        } else {
            Spreadsheet newSpreadsheet = new Spreadsheet(name, rows, cols);

            spreadsheetManager.addSpreadsheet(newSpreadsheet);

            return newSpreadsheet;
        }
    }
}
