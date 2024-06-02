package Server.api;

import Server.model.Spreadsheet;


import java.util.HashMap;
import java.util.Map;

public class CreateNewSpreadsheet {
    private final Map<String, Spreadsheet> spreadsheetMap;

    public CreateNewSpreadsheet() {
        this.spreadsheetMap = new HashMap<>();
    }

    public CreateNewSpreadsheet(Map<String, Spreadsheet> spreadsheetMap) {
        this.spreadsheetMap = spreadsheetMap;
    }

