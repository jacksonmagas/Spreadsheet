package Server.api;

import Server.model.Publisher;
import Server.model.Spreadsheet;
import Server.model.User;

import java.util.List;

public class CreateSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;
    private final PublisherDataService publisherDataService;
    private final UserRegistrationService userRegistrationService;

    public CreateSpreadsheet(SpreadsheetManager spreadsheetManager, PublisherDataService publisherManager, UserRegistrationService userRegistrationService) {
        this.spreadsheetManager = spreadsheetManager;
        this.publisherDataService = publisherManager;
        this.userRegistrationService = userRegistrationService;
    }

    public Spreadsheet createSpreadsheet(String username, List<User> users, String name, int rows, int cols) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Spreadsheet name cannot be null or empty.");
        }
        if (rows < 0 || cols < 0) {
            throw new IllegalArgumentException("Rows and columns must be non-negative.");
        }

        Publisher publisher = userRegistrationService.getPublisher(username);
        if (publisher == null) {
            throw new IllegalArgumentException("Only registered publishers can create spreadsheets.");
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
