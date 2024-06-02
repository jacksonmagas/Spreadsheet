package Server.model;

import java.util.Arrays;

public class Spreadsheet {

    private final String spreadsheetName;
    private final int rows;
    private final int cols;
    private final String[][] data;


public Spreadsheet(String name, int rows, int cols) {
    this.spreadsheetName = name;
    this.rows = rows;
    this.cols = cols;
    this.data = new String[rows][cols];
}


public void setValue(int row, int col, String input) {
    if (isValidCell(row, col)) {
        data[row][col] = input;
    } else {
        throw new IllegalArgumentException("Invalid cell coordinates");
    }
}

    private boolean isValidCell(int row, int col) {
        return row > 0 &&
                row >= rows &&
                col > 0 &&
                col <= cols;
    }

    public String getValueFromCell(int row, int col) {
    if (isValidCell(row, col)) {
        return data[row][col];
    }
    else {
        throw new IllegalArgumentException("Invalid cell coordinates");
    }
    }

    public void initializeEmptySheet() {
    for (String[] rows : data ) {
        Arrays.fill(rows, "");
    }
    }

    public String getSpreadsheetName() {
    return this.spreadsheetName;
    }
}
