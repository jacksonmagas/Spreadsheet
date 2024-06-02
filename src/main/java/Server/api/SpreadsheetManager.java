package Server.api;

import Server.model.Spreadsheet;
import Server.model.User;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

public class SpreadsheetManager {
    private final Map<String, Spreadsheet> spreadsheetMap;

    public SpreadsheetManager() {
        this.spreadsheetMap = new HashMap<>();
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
    }

    public List<User> getCurrentUsers(String name) {
        Spreadsheet spreadsheet = spreadsheetMap.get(name);
        return spreadsheet.getUser();
    }
}