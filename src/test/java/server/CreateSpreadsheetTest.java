package server;

import Server.api.CreateSpreadsheet;
import Server.api.SpreadsheetManager;
import Server.model.Spreadsheet;
import Server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateSpreadsheetTest {

    private CreateSpreadsheet createSpreadsheet;
    private SpreadsheetManager spreadsheetManager;

    @BeforeEach
    public void setUp() {
        spreadsheetManager = new SpreadsheetManager();
        createSpreadsheet = new CreateSpreadsheet(spreadsheetManager);
        spreadsheetManager.clear();
    }

    @Test
    public void testCreateSpreadsheetSuccess() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));
        users.add(new User("user2", "password2", "user2@example.com"));

        final String spreadsheetName = "Test Spreadsheet";
        final int rows = 10;
        final int cols = 10;

        Spreadsheet createdSpreadsheet = createSpreadsheet.createSpreadsheet(users, spreadsheetName, rows, cols);

        assertNotNull(createdSpreadsheet);
        assertEquals(spreadsheetName, createdSpreadsheet.getSpreadsheetName());
        assertEquals(10, createdSpreadsheet.getRows());
        assertEquals(10, createdSpreadsheet.getCols());
        assertEquals(1, spreadsheetManager.getAllSpreadsheets().size());
    }

    @Test
    public void testCreateSpreadsheetThatAlreadyExists() {
        List<User> users = new ArrayList<>();
        final String spreadsheetName = "Existing Spreadsheet";
        spreadsheetManager.addSpreadsheet(new Spreadsheet(users, spreadsheetName, 5, 5));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                createSpreadsheet.createSpreadsheet(users, spreadsheetName, 10, 10));
        assertEquals("Spreadsheet Name Already Exists", thrown.getMessage());
        assertEquals(1, spreadsheetManager.getAllSpreadsheets().size());
    }

    @Test
    public void testCreateSpreadsheetWithInvalidParameters() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));

        final String emptySpreadsheetName = "";
        final int negativeRows = -1;
        final int negativeCols = -1;

        assertThrows(IllegalArgumentException.class, () ->
                createSpreadsheet.createSpreadsheet(users, emptySpreadsheetName, negativeRows, negativeCols));

        final String validSpreadsheetName = "Valid Name";
        assertThrows(IllegalArgumentException.class, () ->
                createSpreadsheet.createSpreadsheet(users, validSpreadsheetName, negativeRows, negativeCols));

        final int positiveRows = 10;
        assertThrows(IllegalArgumentException.class, () ->
                createSpreadsheet.createSpreadsheet(users, validSpreadsheetName, positiveRows, negativeCols));
    }
}