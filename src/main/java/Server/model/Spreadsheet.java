package Server.model;

import Model.Utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spreadsheet {

    private final List<User> users;
    private final String spreadsheetName;
    private final int rows;
    private final int cols;
    private final String[][] data;


public Spreadsheet(List<User> users, String name, int rows, int cols) {
    if (users.size()> 5) {
        throw new IllegalArgumentException("Only 5 users allowed at a time!");
    }
    this.users = new ArrayList<>(users);
    this.spreadsheetName = name;
    this.rows = rows;
    this.cols = cols;
    this.data = new String[rows][cols];
    initializeEmptySheet();
}


public int getRows() {
    return this.rows;
}

public int getCols() {
    return this.cols;
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

    public List<User> getUser() {
    return this.users;
    }


}


