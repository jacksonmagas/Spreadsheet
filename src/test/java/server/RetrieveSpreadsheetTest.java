package server;

import Server.api.RetrieveSpreadsheet;
import Server.api.SpreadsheetManager;
import Server.model.Publisher;
import Server.model.Spreadsheet;
import Server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RetrieveSpreadsheetTest {

    private RetrieveSpreadsheet retrieveSpreadsheet;
    private SpreadsheetManager spreadsheetManager;

    @BeforeEach
    public void setUp() {
        spreadsheetManager = new SpreadsheetManager();
        retrieveSpreadsheet = new RetrieveSpreadsheet(spreadsheetManager);
    }

    @Test
    public void testRetrieveSpreadsheet_Success() {

        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));
        users.add(new User("user2", "password2", "user2@example.com"));
        Publisher publisher = new Publisher("Katie", "mock@gmail");

        Spreadsheet spreadsheet = new Spreadsheet(publisher, users, "Example", 10, 5);

        spreadsheetManager.addSpreadsheet(spreadsheet);


        Spreadsheet retrievedSpreadsheet = retrieveSpreadsheet.retrieveSpreadsheet("Example");

        assertNotNull(retrievedSpreadsheet);
        assertEquals(spreadsheet, retrievedSpreadsheet);
    }

    @Test
    public void testRetrieveSpreadsheet_NonexistentSpreadsheet() {

        assertThrows(IllegalArgumentException.class, () ->
                retrieveSpreadsheet.retrieveSpreadsheet("Nonexistent"));
    }
}