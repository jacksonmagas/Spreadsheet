package server;

import Server.api.DeleteSpreadsheet;
import Server.api.PublisherDataService;
import Server.api.SpreadsheetManager;
import Server.model.Publisher;
import Server.model.Spreadsheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import Server.model.User;

public class DeleteSpreadsheetTest {

    private DeleteSpreadsheet deleteSpreadsheet;
    private SpreadsheetManager spreadsheetManager;


    @BeforeEach
    public void setUp() {
        spreadsheetManager = new SpreadsheetManager();
        deleteSpreadsheet = new DeleteSpreadsheet(spreadsheetManager);
        spreadsheetManager.clear();
    }

    @Test
    public void testDeleteSpreadsheetSuccess() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));
        users.add(new User("user2", "password2", "user2@example.com"));
        Publisher publisher = new Publisher("Katie", "mock@gmail");

        Spreadsheet spreadsheet = new Spreadsheet(publisher, users, "DeleteSpreadsheet", 10, 5);
        spreadsheetManager.addSpreadsheet(spreadsheet);

        assertNotNull(spreadsheet);
        assertEquals(1, spreadsheetManager.getAllSpreadsheets().size());

        deleteSpreadsheet.deleteSpreadsheet("DeleteSpreadsheet");
        assertEquals(0, spreadsheetManager.getAllSpreadsheets().size());
    }

    @Test
    public void testDeleteSpreadsheetThatDoesNotExist() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                deleteSpreadsheet.deleteSpreadsheet("DoesNotExist"));
        assertEquals("Spreadsheet DoesNotExist does not exist in the database.", thrown.getMessage());
    }
}