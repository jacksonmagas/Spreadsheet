package server;

import Server.api.AutoSaveSpreadsheet;
import Server.api.SpreadsheetManager;
import Server.model.Spreadsheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.times;

public class AutoSaveSpreadsheetTest {
    private AutoSaveSpreadsheet autoSaveSpreadsheet;
    private SpreadsheetManager mockSpreadsheetManager;
    private final long TIME_DELAY = 1000; // Set a shorter delay for testing

    @BeforeEach
    public void setUp() {
        mockSpreadsheetManager = Mockito.mock(SpreadsheetManager.class);
        autoSaveSpreadsheet = new AutoSaveSpreadsheet(mockSpreadsheetManager, TIME_DELAY);

        List<Spreadsheet> spreadsheets = new ArrayList<>();
        Spreadsheet mockSpreadsheet = Mockito.mock(Spreadsheet.class);
        spreadsheets.add(mockSpreadsheet);

        Mockito.when(mockSpreadsheetManager.getAllSpreadsheets()).thenReturn(spreadsheets);
    }

    @Test
    public void testAutoSaveBegin() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        Mockito.doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(mockSpreadsheetManager).updateSpreadsheet(Mockito.any(Spreadsheet.class));

        autoSaveSpreadsheet.autoSaveBegin();

        // Wait for the latch to count down to zero
        boolean completed = latch.await(4 * TIME_DELAY, TimeUnit.MILLISECONDS);

        // Stop the auto-save process to clean up
        autoSaveSpreadsheet.autoSaveStop();

        // Verify that the latch reached zero (meaning the expected number of invocations occurred)
        assert completed;

        // Verify that updateSpreadsheet was called three times
        Mockito.verify(mockSpreadsheetManager, times(3)).updateSpreadsheet(Mockito.any(Spreadsheet.class));
    }
}
