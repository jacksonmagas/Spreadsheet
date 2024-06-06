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
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class PerformanceTest {
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
//    public void testServerResponseTime() {
//        List<User> users = new ArrayList<>();
//        users.add(new User("user1", "password1", "user1@example.com"));
//        users.add(new User("user2", "password2", "user2@example.com"));
//        Publisher publisher = new Publisher("Katie", "mock@gmail");
//
//        final String spreadsheetName = "PerformanceTestSpreadsheet";
//        final int rows = 10;
//        final int cols = 10;
//
//        // Start the timer
//        long startTime = System.currentTimeMillis();
//
//        // Perform the server action
//        Spreadsheet createdSpreadsheet = createSpreadsheet.createSpreadsheet(
//                publisher, users, spreadsheetName, rows, cols);
//
//        // Stop the timer
//        long endTime = System.currentTimeMillis();
//
//        // Calculate the elapsed time
//        long elapsedTime = endTime - startTime;
//
//        // Check that the server responded within 10 seconds
//        assertTrue(elapsedTime <= 10000, "Server did not respond within 10 seconds. Elapsed time: " + elapsedTime + "ms");
//    }
//}
