package Server.api;

import Server.model.Publisher;
import Server.model.Spreadsheet;
import Server.model.User;

import java.util.List;

public class CreateSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;
    private final PublisherDataService publisherDataService;

    public CreateSpreadsheet(SpreadsheetManager spreadsheetManager, PublisherDataService publisherManager) {
        this.spreadsheetManager = spreadsheetManager;
        this.publisherDataService = publisherManager;

    }

    public Spreadsheet createSpreadsheet(Publisher publisher, List<User> users, String name, int rows, int cols) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Spreadsheet name cannot be null or empty.");
        }
        if (rows < 0 || cols < 0) {
            throw new IllegalArgumentException("Rows and columns must be non-negative.");
        }
        if (publisher == null) {
            throw new IllegalArgumentException("Publisher can not be null");
        }
        if (spreadsheetManager.containsSpreadsheet(name)) {
            throw new IllegalArgumentException("Spreadsheet Name Already Exists");
        } else {
            Spreadsheet newSpreadsheet = new Spreadsheet(publisher, users, name, rows, cols);
            spreadsheetManager.addSpreadsheet(newSpreadsheet);
            publisherDataService.addPublishedSpreadsheet(publisher, newSpreadsheet);
            return newSpreadsheet;
        }
    }
}
