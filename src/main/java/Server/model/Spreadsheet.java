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
    private final int[][] versions; // Add a version array for cells

    public Spreadsheet(List<User> users, String name, int rows, int cols) {
        if (users.size() > 5) {
            throw new IllegalArgumentException("Only 5 users allowed at a time!");
        }
        if (rows < 0 || cols < 0) {
            throw new IllegalArgumentException("Rows and columns must be non-negative.");
        }
        this.users = new ArrayList<>(users);
        this.spreadsheetName = name;
        this.rows = rows;
        this.cols = cols;
        this.data = new String[rows][cols];
        this.versions = new int[rows][cols]; // Initialize version array
        initializeEmptySheet();
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public void setValue(int row, int col, String input, int version) {
        if (isValidCell(row, col)) {
            if (versions[row][col] != version) {
                throw new IllegalStateException("Outdated cell version");
            }
            if (input.startsWith("=") && !isValidFormula(input)) {
                throw new IllegalArgumentException("Invalid formula");
            }
            data[row][col] = input;
            versions[row][col]++;
        } else {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
    }

    private boolean isValidFormula(String formula) {
        return formula.equals("=VALID_FORMULA()");
    }

    public int getCellVersion(int row, int col) {
        if (isValidCell(row, col)) {
            return versions[row][col];
        } else {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public String getValueFromCell(int row, int col) {
        if (isValidCell(row, col)) {
            return data[row][col];
        } else {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
    }

    public void initializeEmptySheet() {
        for (String[] row : data) {
            Arrays.fill(row, "");
        }
        for (int[] row : versions) {
            Arrays.fill(row, 0);
        }
    }

    public String getSpreadsheetName() {
        return this.spreadsheetName;
    }

    public List<User> getUser() {
        return this.users;
    }
}
