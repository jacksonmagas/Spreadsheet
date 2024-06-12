package com.example.huskysheet.model;

import java.util.List;

public class Publisher {
    private String name;
    private List<Spreadsheet> spreadsheets;

    public Publisher(String name, List<Spreadsheet> spreadsheets) {
        this.name = name;
        this.spreadsheets = spreadsheets;
    }

    public String getName() {
        return name;
    }

    public void addSpreadsheet(Spreadsheet spreadsheet) {
        this.spreadsheets.add(spreadsheet);
    }

    public void removeSpreadsheet(Spreadsheet spreadsheet) {
        if (spreadsheets.contains(spreadsheet)) {
            this.spreadsheets.remove(spreadsheet);
        }
    }

    public List<Spreadsheet> getSpreadsheets() {
        return this.spreadsheets;
    }

  public void setSpreadsheets(List<Spreadsheet> sheets1) {
        this.spreadsheets = sheets1;
  }
}