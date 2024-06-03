package server;

import Server.api.AutoSaveSpreadsheet;
import Server.api.SpreadsheetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import Server.model.Spreadsheet;

import static org.mockito.Mockito.times;

public class AutoSaveSpreadsheetTest {
    private AutoSaveSpreadsheet autoSaveSpreadsheet;
    private SpreadsheetManager mockSpreadsheetManager;

    @BeforeEach
    public void setUp() {
        mockSpreadsheetManager = Mockito.mock(SpreadsheetManager.class);
        autoSaveSpreadsheet = new AutoSaveSpreadsheet(mockSpreadsheetManager);
    }

    @Test
    public void testAutoSaveBegin() throws InterruptedException {

        autoSaveSpreadsheet.autoSaveBegin();

        // Wait for autosave to trigger multiple times
        Thread.sleep(15000);

        // Verify that saveSpreadsheet was called multiple times
        Mockito.verify(mockSpreadsheetManager,
                times(3)).updateSpreadsheet(Mockito.any(Spreadsheet.class));
    }
}