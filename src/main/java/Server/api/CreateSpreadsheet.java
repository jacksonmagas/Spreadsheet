package Server.api;

import Server.model.Spreadsheet;
import Server.model.User;

import java.util.List;

public class CreateSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;

    public CreateSpreadsheet(SpreadsheetManager spreadsheetManager) {
        this.spreadsheetManager = spreadsheetManager;
    }

    public Spreadsheet createSpreadsheet(List<User> users, String name, int rows, int cols) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Spreadsheet name cannot be null or empty.");
        }
        if (rows < 0 || cols < 0) {
            throw new IllegalArgumentException("Rows and columns must be non-negative.");
        }
        if (spreadsheetManager.containsSpreadsheet(name)) {
            throw new IllegalArgumentException("Spreadsheet Name Already Exists");
        } else {
            Spreadsheet newSpreadsheet = new Spreadsheet(users, name, rows, cols);
            spreadsheetManager.addSpreadsheet(newSpreadsheet);
            return newSpreadsheet;
        }
    }
}
