package Server.api;



public class LockCell {
private final SpreadsheetManager spreadsheetManager;
private boolean[][] editingStatus;

    public LockCell(SpreadsheetManager spreadsheetManager, boolean[][] editingStatus) {
        this.spreadsheetManager = spreadsheetManager;
        this.editingStatus = editingStatus;
    }

    public boolean isCellBeingEdited(String spreadsheetName, int row, int col) {
        synchronized(this) {
        if (spreadsheetManager.containsSpreadsheet(spreadsheetName)) {
            return editingStatus[row][col];
        } else {
            throw new IllegalArgumentException("Spreadsheet does not exist");
        }
    }
    }

    public void setCellStatus(String spreadsheetName,int row, int col, boolean isBeingEdited) {
        synchronized (this) {
            if (!isCellLocked(spreadsheetName, row, col)) {
                editingStatus[row][col] = isBeingEdited;
            }
            else {
                throw new IllegalArgumentException("Cell is Locked and Can not be Edited");
            }
        }
    }



    private boolean isCellLocked(String spreadsheetName, int row, int col) {
        return isCellBeingEdited(spreadsheetName, row, col);
    }
}


