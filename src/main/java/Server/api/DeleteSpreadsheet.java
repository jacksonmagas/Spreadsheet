package Server.api;

public class DeleteSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;

    public DeleteSpreadsheet(SpreadsheetManager spreadsheetManager) {
        this.spreadsheetManager = spreadsheetManager;
    }

    public void deleteSpreadsheet(String uniqueSpreadsheetName) {
        if (!spreadsheetManager.containsSpreadsheet(uniqueSpreadsheetName)) {
            System.out.println("There is no Spreadsheet named" + uniqueSpreadsheetName +
                    "in the Database");
        }
        else {
            spreadsheetManager.removeSpreadsheet(uniqueSpreadsheetName);
            System.out.println(uniqueSpreadsheetName + "has been removed from database");
        }
    }
}
