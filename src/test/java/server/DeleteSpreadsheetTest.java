package server;
import Server.api.CreateSpreadsheet;
import Server.api.DeleteSpreadsheet;
import Server.api.SpreadsheetManager;
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

        spreadsheetManager.clear();
    }

    @Test
    public void testCreateSpreadsheetSuccess() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));
        users.add(new User("user2", "password2", "user2@example.com"));

        Spreadsheet spreadsheet = new Spreadsheet(users, "DeleteSpreadsheet", 10, 5);

        assertNotNull(spreadsheet);
        assertEquals(1, spreadsheetManager.getAllSpreadsheets().size());

        spreadsheetManager.removeSpreadsheet("DeleteSpreadsheet");
        assertEquals(0, spreadsheetManager.getAllSpreadsheets().size());
    }


    @Test
    public void testDeleteSpreadsheetThatDoesNotExist() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));

        Spreadsheet spreadsheet = new Spreadsheet(users, "Example", 10, 20);
        assertThrows(IllegalArgumentException.class, () ->
                deleteSpreadsheet.deleteSpreadsheet("Doesn't Exist"));
    }
}
