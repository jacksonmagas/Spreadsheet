package server;//package server;
//
//import Server.api.CreateSpreadsheet;
//import Server.api.PublisherDataService;
//import Server.api.SpreadsheetManager;
//import Server.model.Publisher;
//import Server.model.Spreadsheet;
//import Server.model.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CreateSpreadsheetTest {
//
//    private CreateSpreadsheet createSpreadsheet;
//    private SpreadsheetManager spreadsheetManager;
//    private PublisherDataService dataService;
//
//    @BeforeEach
//    public void setUp() {
//        spreadsheetManager = new SpreadsheetManager();
//        createSpreadsheet = new CreateSpreadsheet(spreadsheetManager, dataService);
//        spreadsheetManager.clear();
//    }
//
//    @Test
//    public void testCreateSpreadsheetSuccess() {
//        List<User> users = new ArrayList<>();
//        users.add(new User("user1", "password1", "user1@example.com"));
//        users.add(new User("user2", "password2", "user2@example.com"));
//        Publisher publisher = new Publisher("Katie", "mock@gmail");
//
//        final String spreadsheetName = "Test Spreadsheet";
//        final int rows = 10;
//        final int cols = 10;
//
//        Spreadsheet createdSpreadsheet = createSpreadsheet.createSpreadsheet(
//                publisher, users, spreadsheetName, rows, cols);
//
//        assertNotNull(createdSpreadsheet);
//        assertEquals(spreadsheetName, createdSpreadsheet.getSpreadsheetName());
//        assertEquals(10, createdSpreadsheet.getRows());
//        assertEquals(10, createdSpreadsheet.getCols());
//        assertEquals(1, spreadsheetManager.getAllSpreadsheets().size());
//    }
//
//    @Test
//    public void testCreateSpreadsheetThatAlreadyExists() {
//        List<User> users = new ArrayList<>();
//        final String spreadsheetName = "Existing Spreadsheet";
//        Publisher publisher = new Publisher("Katie", "mock@gmail");
//        spreadsheetManager.addSpreadsheet(new Spreadsheet(publisher, users, spreadsheetName, 5, 5));
//
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
//                createSpreadsheet.createSpreadsheet(publisher, users, spreadsheetName, 10, 10));
//        assertEquals("Spreadsheet Name Already Exists", thrown.getMessage());
//        assertEquals(1, spreadsheetManager.getAllSpreadsheets().size());
//    }
//
//    @Test
//    public void testCreateSpreadsheetWithInvalidParameters() {
//        List<User> users = new ArrayList<>();
//        users.add(new User("user1", "password1", "user1@example.com"));
//        Publisher publisher = new Publisher("Katie", "mock@gmail");
//
//        final String emptySpreadsheetName = "";
//        final int negativeRows = -1;
//        final int negativeCols = -1;
//
//        assertThrows(IllegalArgumentException.class, () ->
//                createSpreadsheet.createSpreadsheet(publisher, users, emptySpreadsheetName, negativeRows, negativeCols));
//
//        final String validSpreadsheetName = "Valid Name";
//        assertThrows(IllegalArgumentException.class, () ->
//                createSpreadsheet.createSpreadsheet(publisher, users, validSpreadsheetName, negativeRows, negativeCols));
//
//        final int positiveRows = 10;
//        assertThrows(IllegalArgumentException.class, () ->
//                createSpreadsheet.createSpreadsheet(publisher, users, validSpreadsheetName, positiveRows, negativeCols));
//    }
//}