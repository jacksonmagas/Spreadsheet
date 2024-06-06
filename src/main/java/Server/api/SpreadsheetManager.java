/*
package Server.api;

import Server.model.Publisher;
import Server.model.Spreadsheet;
import Server.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

public class SpreadsheetManager {
    private final Map<String, Spreadsheet> spreadsheetMap;
    private final EditSessionManager editSessionManager;

    public SpreadsheetManager() {
        this.spreadsheetMap = new HashMap<>();
        this.editSessionManager = new EditSessionManager();
    }

    public void addSpreadsheet(Spreadsheet spreadsheet) {
        spreadsheetMap.put(spreadsheet.getSpreadsheetName(), spreadsheet);
    }

    public Spreadsheet getSpreadsheet(String uniqueName) {
        return spreadsheetMap.get(uniqueName);
    }

    public void removeSpreadsheet(String uniqueName) {
        spreadsheetMap.remove(uniqueName);
    }

    public void updateSpreadsheet(Spreadsheet spreadsheet) {
        spreadsheetMap.put(spreadsheet.getSpreadsheetName(), spreadsheet);
    }

    public Collection<Spreadsheet> getAllSpreadsheets() {
        return spreadsheetMap.values();
    }

    public boolean containsSpreadsheet(String uniqueName) {
        return spreadsheetMap.containsKey(uniqueName);
    }

    public void clear() {
        spreadsheetMap.clear();
        editSessionManager.clear();
    }

    public List<User> getCurrentUsers(String name) {
        Spreadsheet spreadsheet = spreadsheetMap.get(name);
        return spreadsheet.getUser();
    }

    public synchronized void editCell(String spreadsheetName, int row, int column, String newValue, int clientVersion) {
        Spreadsheet spreadsheet = spreadsheetMap.get(spreadsheetName);
        if (spreadsheet != null) {
            spreadsheet.setValue(row, column, newValue, clientVersion);
            updateSpreadsheet(spreadsheet);
        } else {
            throw new IllegalArgumentException("Spreadsheet does not exist!");
        }
    }

    public synchronized int getCellVersion(String spreadsheetName, int row, int col) {
        Spreadsheet spreadsheet = spreadsheetMap.get(spreadsheetName);
        if (spreadsheet != null) {
            return spreadsheet.getCellVersion(row, col);
        } else {
            throw new IllegalArgumentException("Spreadsheet does not exist!");
        }
    }

    public void startEditing(String spreadsheetName, String userName) {
        editSessionManager.startEditing(spreadsheetName, userName);
    }

    public void stopEditing(String spreadsheetName, String userName) {
        editSessionManager.stopEditing(spreadsheetName, userName);
    }

    public String getEditor(String spreadsheetName) {
        return editSessionManager.getEditor(spreadsheetName);
    }
}
*/