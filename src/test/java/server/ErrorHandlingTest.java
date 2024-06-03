package server;

import Server.api.CreateSpreadsheet;
import Server.api.DeleteSpreadsheet;
import Server.api.SpreadsheetManager;
import Server.model.Spreadsheet;
import Server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorHandlingTest {

    private CreateSpreadsheet createSpreadsheet;
    private DeleteSpreadsheet deleteSpreadsheet;
    private SpreadsheetManager spreadsheetManager;

    @BeforeEach
    public void setUp() {
        spreadsheetManager = new SpreadsheetManager();
        createSpreadsheet = new CreateSpreadsheet(spreadsheetManager);
        deleteSpreadsheet = new DeleteSpreadsheet(spreadsheetManager);
        spreadsheetManager.clear();
    }

    @Test
    public void testInvalidSpreadsheetName() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));

        assertThrows(IllegalArgumentException.class, () ->
                createSpreadsheet.createSpreadsheet(users, "", 10, 10));

        assertThrows(IllegalArgumentException.class, () ->
                createSpreadsheet.createSpreadsheet(users, null, 10, 10));
    }

    @Test
    public void testInvalidCellCoordinates() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));

        final String spreadsheetName = "TestInvalidCoordinates";
        Spreadsheet spreadsheet = createSpreadsheet.createSpreadsheet(users, spreadsheetName, 5, 5);

        assertThrows(IllegalArgumentException.class, () ->
                spreadsheet.setValue(-1, 0, "Invalid", 0));

        assertThrows(IllegalArgumentException.class, () ->
                spreadsheet.setValue(0, -1, "Invalid", 0));

        assertThrows(IllegalArgumentException.class, () ->
                spreadsheet.setValue(5, 0, "Invalid", 0));

        assertThrows(IllegalArgumentException.class, () ->
                spreadsheet.setValue(0, 5, "Invalid", 0));
    }

    @Test
    public void testOutdatedCellVersion() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));

        final String spreadsheetName = "TestOutdatedVersion";
        Spreadsheet spreadsheet = createSpreadsheet.createSpreadsheet(users, spreadsheetName, 5, 5);

        spreadsheet.setValue(0, 0, "Initial", 0);
        assertThrows(IllegalStateException.class, () ->
                spreadsheet.setValue(0, 0, "Outdated", 0));
    }

    @Test
    public void testInvalidFormula() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));

        final String spreadsheetName = "TestInvalidFormula";
        Spreadsheet spreadsheet = createSpreadsheet.createSpreadsheet(users, spreadsheetName, 5, 5);

        assertThrows(IllegalArgumentException.class, () ->
                spreadsheet.setValue(0, 0, "=INVALID_FORMULA()", 0));
    }

    @Test
    public void testDeleteNonExistentSpreadsheet() {
        final String spreadsheetName = "NonExistentSpreadsheet";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                deleteSpreadsheet.deleteSpreadsheet(spreadsheetName));

        assertEquals("Spreadsheet NonExistentSpreadsheet does not exist in the database.", thrown.getMessage());
    }
}
