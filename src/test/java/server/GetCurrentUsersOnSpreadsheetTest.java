package server;

import Server.api.GetCurrentUsersOnSpreadsheet;
import Server.api.SpreadsheetManager;
import Server.model.Spreadsheet;
import Server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetCurrentUsersOnSpreadsheetTest {

    private GetCurrentUsersOnSpreadsheet getCurrentUsersOnSpreadsheet;
    private SpreadsheetManager spreadsheetManager;

    @BeforeEach
    public void setUp() {
        spreadsheetManager = new SpreadsheetManager();
        getCurrentUsersOnSpreadsheet = new GetCurrentUsersOnSpreadsheet(spreadsheetManager);
    }

    @Test
    public void testGetUsersSuccess() {
        ArrayList<User> users = new ArrayList<>();
        User user1 = new User("user1", "password1", "user1@example.com");
        User user2 = new User("user2", "password2", "user2@example.com");
        User user3 = new User("user3", "password3", "user3@example.com");
        User user4 = new User("user4", "password4", "user4@example.com");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        Spreadsheet spreadsheet = new Spreadsheet(users, "Example", 20, 30);
        spreadsheetManager.addSpreadsheet(spreadsheet);

        List<User> retrievedUsers = getCurrentUsersOnSpreadsheet.getUsers("Example");

        assertNotNull(retrievedUsers);
        assertEquals(4, retrievedUsers.size());
        assertTrue(retrievedUsers.contains(user2));
    }

    @Test
    public void testIsUserOnSpreadsheet_UserIsOnSpreadsheet() {
        // Create a test user
        User user = new User("user1", "password1", "user1@example.com");

        // Create a list of users containing the test user
        List<User> users = new ArrayList<>();
        users.add(user);

        // Check if the test user is on the spreadsheet
        assertTrue(getCurrentUsersOnSpreadsheet.isUserOnSpreadsheet(users, user));
    }

    @Test
    public void testIsUserOnSpreadsheet() {

        User user = new User("test1", "test2", "user1@example.com");

        List<User> users = new ArrayList<>();

        assertFalse(getCurrentUsersOnSpreadsheet.isUserOnSpreadsheet(users, user));

        users.add(user);
        assertTrue(getCurrentUsersOnSpreadsheet.isUserOnSpreadsheet(users, user));
    }
}