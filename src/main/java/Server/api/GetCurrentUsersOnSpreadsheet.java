/*
package Server.api;
import Server.model.User;

import java.util.List;

public class GetCurrentUsersOnSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;

    public GetCurrentUsersOnSpreadsheet(SpreadsheetManager spreadsheetManager) {
        this.spreadsheetManager = spreadsheetManager;
    }

    public List<User> getUsers(String uniqueSpreadsheetName) {
        if (spreadsheetManager.containsSpreadsheet(uniqueSpreadsheetName)) {
            return spreadsheetManager.getCurrentUsers(uniqueSpreadsheetName);
        } else {
            throw new IllegalArgumentException("Spreadsheet does not exist!");
        }
    }

    public boolean isUserOnSpreadsheet(List<User> users, User user) {
        return users.contains(user);
    }
}
*/