package Server.api;

import Server.model.Spreadsheet;
import Server.model.User;

import java.util.List;

public class CreateSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;

    public CreateSpreadsheet() {
        this.spreadsheetManager = new SpreadsheetManager();
    }

    public Spreadsheet createSpreadsheet(List<User> user, String name, int rows, int cols) {
        if (spreadsheetManager.containsSpreadsheet(name)) {
            throw new IllegalArgumentException("Spreadsheet Name Already Exists");
        } else {
            Spreadsheet newSpreadsheet = new Spreadsheet(user, name, rows, cols);

            spreadsheetManager.addSpreadsheet(newSpreadsheet);

            return newSpreadsheet;
        }
    }
}
