package Server.api;

import Server.model.Spreadsheet;

import java.util.Timer;
import java.util.TimerTask;

// Saves a Spreadsheet every 5 seconds
public class AutoSaveSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;
    private final long timeDelay = 5000;

    public AutoSaveSpreadsheet(SpreadsheetManager spreadsheetManager) {
        this.spreadsheetManager = spreadsheetManager;
    }

    public void autoSaveBegin() {
        Timer timer = new Timer();
        timer.schedule(new AutoSaveTask(), timeDelay, timeDelay);
    }

    private class AutoSaveTask extends TimerTask {
        @Override
        public void run() {

            for (Spreadsheet spreadsheet : spreadsheetManager.getAllSpreadsheets()) {
                saveSpreadsheet(spreadsheet);
            }
        }
    }

    public void saveSpreadsheet(Spreadsheet spreadsheet) {
        spreadsheetManager.updateSpreadsheet(spreadsheet);
    }
}
