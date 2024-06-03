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

public class SheetEditingTrackingTest {

    private SpreadsheetManager spreadsheetManager;
    private CreateSpreadsheet createSpreadsheet;

    @BeforeEach
    public void setUp() {
        spreadsheetManager = new SpreadsheetManager();
        createSpreadsheet = new CreateSpreadsheet(spreadsheetManager);
        spreadsheetManager.clear();
    }

    @Test
    public void testStartEditingSession() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));

        Spreadsheet spreadsheet = createSpreadsheet.createSpreadsheet(users, "TestSheet", 10, 10);
        spreadsheetManager.startEditing("TestSheet", "user1");

        assertEquals("user1", spreadsheetManager.getEditor("TestSheet"));
    }

    @Test
    public void testStopEditingSession() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));

        Spreadsheet spreadsheet = createSpreadsheet.createSpreadsheet(users, "TestSheet", 10, 10);
        spreadsheetManager.startEditing("TestSheet", "user1");
        assertEquals("user1", spreadsheetManager.getEditor("TestSheet"));

        spreadsheetManager.stopEditing("TestSheet", "user1");
        assertNull(spreadsheetManager.getEditor("TestSheet"));
    }

    @Test
    public void testMultipleEditingSessions() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));
        users.add(new User("user2", "password2", "user2@example.com"));

        Spreadsheet spreadsheet1 = createSpreadsheet.createSpreadsheet(users, "TestSheet1", 10, 10);
        Spreadsheet spreadsheet2 = createSpreadsheet.createSpreadsheet(users, "TestSheet2", 10, 10);

        spreadsheetManager.startEditing("TestSheet1", "user1");
        spreadsheetManager.startEditing("TestSheet2", "user2");

        assertEquals("user1", spreadsheetManager.getEditor("TestSheet1"));
        assertEquals("user2", spreadsheetManager.getEditor("TestSheet2"));

        spreadsheetManager.stopEditing("TestSheet1", "user1");
        assertNull(spreadsheetManager.getEditor("TestSheet1"));

        spreadsheetManager.stopEditing("TestSheet2", "user2");
        assertNull(spreadsheetManager.getEditor("TestSheet2"));
    }

    @Test
    public void testEditSessionByDifferentUser() {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));

        Spreadsheet spreadsheet = createSpreadsheet.createSpreadsheet(users, "TestSheet", 10, 10);
        spreadsheetManager.startEditing("TestSheet", "user1");
        assertEquals("user1", spreadsheetManager.getEditor("TestSheet"));

        spreadsheetManager.startEditing("TestSheet", "user2");
        assertEquals("user2", spreadsheetManager.getEditor("TestSheet"));
    }
}
