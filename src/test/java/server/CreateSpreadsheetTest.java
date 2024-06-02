package server;

import Server.api.CreateSpreadsheet;
import Server.api.SpreadsheetManager;
import Server.model.Spreadsheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import Server.model.User;

public class CreateSpreadsheetTest {

    private CreateSpreadsheet createSpreadsheet;
    private SpreadsheetManager spreadsheetManager;

    @BeforeEach
    public void setUp() {
        spreadsheetManager = new SpreadsheetManager();
        createSpreadsheet = new CreateSpreadsheet();
        spreadsheetManager.clear();
    }

    @Test
    public void testCreateSpreadsheetSuccess() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));
        users.add(new User("user2", "password2", "user2@example.com"));

        String spreadsheetName = "Test Spreadsheet";
        int rows = 10;
        int cols = 10;

        Spreadsheet createdSpreadsheet = createSpreadsheet.createSpreadsheet(users, spreadsheetName, rows, cols);

        assertNotNull(createdSpreadsheet);
        assertEquals(spreadsheetName, createdSpreadsheet.getSpreadsheetName());
        assertEquals(10, createdSpreadsheet.getRows());
        assertEquals(10, createdSpreadsheet.getCols());
        assertEquals(100, (createdSpreadsheet.getRows() * createdSpreadsheet.getCols()));
        assertEquals(1, spreadsheetManager.getAllSpreadsheets().size());
    }

    @Test
    public void testCreateSpreadsheetThatAlreadyExists() {

        List<User> users = new ArrayList<>();
        String spreadsheetName = "Existing Spreadsheet";
        spreadsheetManager.addSpreadsheet(new Spreadsheet(users, spreadsheetName, 5, 5));


        assertThrows(IllegalArgumentException.class, () ->
                createSpreadsheet.createSpreadsheet(users, spreadsheetName, 10, 10));
        assertEquals(1, spreadsheetManager.getAllSpreadsheets().size());
    }
}