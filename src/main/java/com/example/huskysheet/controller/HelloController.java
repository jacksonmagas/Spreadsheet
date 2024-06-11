package com.example.huskysheet.controller;

import com.example.huskysheet.client.Model.ICell;
import com.example.huskysheet.client.Model.Spreadsheet;
import com.example.huskysheet.client.SpreadsheetManager;
import com.example.huskysheet.client.Utils.SpreadsheetSliceView;
import com.example.huskysheet.client.Utils.SpreadsheetSliceView.Direction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TableView<List<ICell>> table;

    @FXML
    private Menu openRecentMenu;

    @FXML
    private MenuBar menuBar;

    private SpreadsheetManager spreadsheetManager;

    private String userName;
    private String password;
    private String url;

    // Define the number of rows and columns
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private Spreadsheet spreadsheet;

    ObservableList<List<ICell>> list = FXCollections.observableArrayList();

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        // Check if necessary fields are set
//        if (this.url == null || this.userName == null || this.password == null) {
//            System.err.println("URL, username, or password not set!");
//            return;
//        }
//
//        // Initialize the SpreadsheetManager with server URL, username, and password
//        try {
//            spreadsheetManager = new SpreadsheetManager(this.url, this.userName, this.password);
//            // Register the user
//            spreadsheetManager.register();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        // Set up the table
//        setupTable();
//
//        // Dynamically add items to the "Open Recent" submenu
//        addItemsToOpenRecentMenu();
    }


    public void init() {
        // Check if necessary fields are set
        if (this.url == null || this.userName == null || this.password == null) {
            System.err.println("URL, username, or password not set!");
            return;
        }

        // Initialize the SpreadsheetManager with server URL, username, and password
        try {
            spreadsheetManager = new SpreadsheetManager(this.url, this.userName, this.password);
            // Register the user
            spreadsheetManager.register();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Set up the table
        setupTable();

        // Dynamically add items to the "Open Recent" submenu
        addItemsToOpenRecentMenu();
    }

    private void setupTable() {
        spreadsheet = new Spreadsheet();

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);

        // Create the table columns dynamically
        for (int i = 1; i <= COLUMNS; i++) {
            TableColumn<List<ICell>, String> column = new TableColumn<>("Column " + i);
            setupColumn(column, i);
            table.getColumns().add(column);
        }

        // Populate the table with CellFormat objects
        for (int row = 1; row <= ROWS; row++) {
            list.add(new SpreadsheetSliceView(spreadsheet, Direction.row, row));
        }

        table.setItems(list);
    }

    private void addItemsToOpenRecentMenu() {
        try {
            // Get the list of publishers
            List<String> publishers = spreadsheetManager.getPublishers();
            for (String publisher : publishers) {
                // Get the list of sheets for each publisher
                List<String> sheets = spreadsheetManager.getAvailableSheets(publisher);
                // Create a submenu for each publisher
                Menu publisherMenu = new Menu(publisher);
                for (String sheet : sheets) {
                    // Add a menu item for each sheet
                    MenuItem sheetItem = new MenuItem(sheet);
                    publisherMenu.getItems().add(sheetItem);
                }
                openRecentMenu.getItems().add(publisherMenu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set up a column of the spreadsheet.
     * Create a cell value factory which gets the data from the cell at that row/column.
     * Create a cell factory which sets each cell as an editable cell
     * Set the callback for edits in this column to edit the cell
     * @param column the column to set up
     * @param columnNumber the column number
     */
    private void setupColumn(TableColumn<List<ICell>, String> column, int columnNumber) {
        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(columnNumber).getData()));
        column.setEditable(true);
        column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        column.setOnEditCommit(event -> {
            event.getRowValue().get(columnNumber).updateCell(event.getNewValue());
            table.refresh();
        });
    }
}