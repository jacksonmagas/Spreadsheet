/*
package Server.api;

import Server.model.Spreadsheet;

public class RetrieveSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;

    public RetrieveSpreadsheet(SpreadsheetManager spreadsheetManager) {
        this.spreadsheetManager = spreadsheetManager;
    }

    public Spreadsheet retrieveSpreadsheet(String uniqueSpreadsheetName) {
        if (spreadsheetManager.containsSpreadsheet(uniqueSpreadsheetName)) {
            return spreadsheetManager.getSpreadsheet(uniqueSpreadsheetName);
        }
        else {
            throw new IllegalArgumentException("Spreadsheet does not exist!");
        }
    }
}
*/