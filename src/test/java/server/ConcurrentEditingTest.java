package server;//package server;
//
//import Server.api.SpreadsheetManager;
//import Server.api.UpdateSpreadsheet;
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
//public class ConcurrentEditingTest {
//
//    private SpreadsheetManager spreadsheetManager;
//    private Spreadsheet spreadsheet;
//    private UpdateSpreadsheet updateSpreadsheet;
//
//    @BeforeEach
//    public void setUp() {
//        spreadsheetManager = new SpreadsheetManager();
//        updateSpreadsheet = new UpdateSpreadsheet(spreadsheetManager);
//        Publisher publisher = new Publisher("Katie", "mock@gmail");
//
//        List<User> users = new ArrayList<>();
//        users.add(new User("user1", "password1", "user1@example.com"));
//        users.add(new User("user2", "password2", "user2@example.com"));
//
//        spreadsheet = new Spreadsheet(publisher, users, "ConcurrentSheet", 10, 10);
//        spreadsheetManager.addSpreadsheet(spreadsheet);
//    }
//
//    @Test
//    public void testConcurrentEditSuccess() {
//        int initialVersion = spreadsheet.getCellVersion(0, 0);
//
//        // User1 edits the cell
//        assertDoesNotThrow(() ->
//                updateSpreadsheet.editCells("ConcurrentSheet", 0, 0, "User1Edit", initialVersion)
//        );
//
//        // User2 retrieves the updated version and edits the cell again
//        int updatedVersion = spreadsheet.getCellVersion(0, 0);
//        assertDoesNotThrow(() ->
//                updateSpreadsheet.editCells("ConcurrentSheet", 0, 0, "User2Edit", updatedVersion)
//        );
//
//        // Verify the final value and version
//        assertEquals("User2Edit", spreadsheet.getValueFromCell(0, 0));
//        assertEquals(updatedVersion + 1, spreadsheet.getCellVersion(0, 0));
//    }
//
//    @Test
//    public void testConcurrentEditFailure() {
//        int initialVersion = spreadsheet.getCellVersion(0, 0);
//
//        // User1 edits the cell
//        updateSpreadsheet.editCells("ConcurrentSheet", 0, 0, "User1Edit", initialVersion);
//
//        // User2 tries to edit with the old version
//        int outdatedVersion = initialVersion;
//        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
//            updateSpreadsheet.editCells("ConcurrentSheet", 0, 0, "User2Edit", outdatedVersion);
//        });
//
//        assertEquals("Outdated cell version", thrown.getMessage());
//
//        // Verify the value and version remain unchanged
//        assertEquals("User1Edit", spreadsheet.getValueFromCell(0, 0));
//        assertEquals(initialVersion + 1, spreadsheet.getCellVersion(0, 0));
//    }
//}
//
