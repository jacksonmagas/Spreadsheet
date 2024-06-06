/*
package Server.api;

public class DeleteSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;

    public DeleteSpreadsheet(SpreadsheetManager spreadsheetManager) {
        this.spreadsheetManager = spreadsheetManager;
    }

    public void deleteSpreadsheet(String uniqueSpreadsheetName) {
        if (!spreadsheetManager.containsSpreadsheet(uniqueSpreadsheetName)) {
            throw new IllegalArgumentException("Spreadsheet " + uniqueSpreadsheetName + " does not exist in the database.");
        } else {
            spreadsheetManager.removeSpreadsheet(uniqueSpreadsheetName);
            System.out.println(uniqueSpreadsheetName + " has been removed from database.");
        }
    }
}
*/