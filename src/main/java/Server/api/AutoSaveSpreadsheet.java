/*
package Server.api;

import Server.model.Spreadsheet;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class AutoSaveSpreadsheet {
    private final SpreadsheetManager spreadsheetManager;
    private final Timer timer;
    private final long timeDelay;

    public AutoSaveSpreadsheet(SpreadsheetManager spreadsheetManager, long timeDelay) {
        this.spreadsheetManager = spreadsheetManager;
        this.timer = new Timer(true); // Daemon timer to auto-save periodically
        this.timeDelay = timeDelay;
    }

    public void autoSaveBegin() {
        timer.scheduleAtFixedRate(new AutoSaveTask(), 0, timeDelay); // Auto-save at the specified interval
    }

    public void autoSaveStop() {
        timer.cancel();
    }

    private class AutoSaveTask extends TimerTask {
        @Override
        public void run() {
            Collection<Spreadsheet> spreadsheets = spreadsheetManager.getAllSpreadsheets();
            for (Spreadsheet spreadsheet : spreadsheets) {
                spreadsheetManager.updateSpreadsheet(spreadsheet);
            }
        }
    }
}
*/