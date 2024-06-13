package com.example.huskysheet.controller;

import com.example.huskysheet.client.APICallException;
import com.example.huskysheet.client.Model.ISpreadsheet;

import com.example.huskysheet.client.SpreadsheetManager;
import com.example.huskysheet.client.Utils.Conversions;
import com.example.huskysheet.client.Utils.SpreadsheetSliceView;
import com.example.huskysheet.client.Utils.SpreadsheetSliceView.Direction;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.util.Duration;
import javafx.util.converter.DefaultStringConverter;

public class HelloController implements Initializable {
    @FXML
    private TableView<SpreadsheetSliceView> table;
    @FXML
    private TableColumn<SpreadsheetSliceView, String> rowNumbers;
    @FXML
    private Button newColumnButton;
    @FXML
    private Button newRowButton;
    @FXML
    private Menu openRecentMenu;
    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem newSheetMenuItem;

    @FXML
    private MenuItem deleteSheetMenuItem;

    private SpreadsheetManager spreadsheetManager;

    private String copyBuffer;

    // Define the number of rows and columns
    private static final int INITIAL_ROW_NUM = 8;
    private static final int INITIAL_COLUMN_NUM = 8;
    private int numRows;
    private int numCols;
    private ISpreadsheet spreadsheet;

    ObservableList<SpreadsheetSliceView> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Initialize spreadsheet manager with username, url, and password from args.
     * Create table from initial sheet if provided.
     * @author Nikita Clark
     */
    public void init(String url, String userName, String password,
        String initialSheetPublisher, String initialSheetName) {
        // Check if necessary fields are set
        if (url == null || userName == null || password == null) {
            System.err.println("URL, username, or password not set!");
            return;
        }

        // Initialize the SpreadsheetManager with server URL, username, and password
        try {
            spreadsheetManager = new SpreadsheetManager(url, userName, password);
            // Register the user
            spreadsheetManager.register();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (initialSheetPublisher != null && initialSheetName != null) {
            try {
                spreadsheetManager.getSpreadsheet(initialSheetPublisher, initialSheetName);
            } catch (APICallException e) {
                System.err.println("Failed to load initial sheet");
            }
        }

        // setup initial table
        setupTable();

        // Dynamically add items to the "Open Recent" submenu
        addItemsToOpenRecentMenu();

        // Set event handlers for the menu items
        newSheetMenuItem.setOnAction(event -> createSheet());
        deleteSheetMenuItem.setOnAction(event -> deleteSheet());

        // set timer for updating the sheet from the server
        var updates = new Timeline();
        updates.getKeyFrames().add(new KeyFrame(new Duration(3000), event -> {
            try {
                if (spreadsheet != null) {
                    spreadsheetManager.tryGetUpdates();
                }
            } catch (APICallException e) {
                System.err.println("Failed to load updates: " + e.getMessage());
            }
        }));
        updates.setCycleCount(Timeline.INDEFINITE);
        updates.play();
    }

    /**
     * Clear the current columns of the table then initialize the table based on the current spreadsheet.
     * @author Jackson Magas
     */
    private void setupTable() {
        // check that there is a spreadsheet
        if (spreadsheet == null) {
            return;
        }

        // clear current table
        table.getColumns().clear();
        table.getItems().clear();
        numCols = 0;
        numRows = 0;


        table.setEditable(true);
        String headerStyle = "-fx-background-color: -fx-body-color; -fx-font-weight: bold; -fx-text-alignment: center;";

        table.getSelectionModel().setCellSelectionEnabled(true);
        Callback<TableColumn<SpreadsheetSliceView, String>, TableCell<SpreadsheetSliceView, String>> rowNumFactory =
                (tv) -> new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                            setStyle(headerStyle);
                        } else {
                            setText(item);
                            setStyle(headerStyle);
                        }
                        // Prevent cell from being selectable
                        setMouseTransparent(true);
                        setFocusTraversable(false);
                    }
                };
        // Add a listener to the selection model
        table.getSelectionModel().getSelectedCells().addListener(
                new ListChangeListener<>() {
                    @Override
                    public void onChanged(Change<? extends TablePosition> c) {
                        if (c.next()
                                && !c.getAddedSubList().isEmpty()
                                && c.getAddedSubList().getFirst().getColumn() == 0) {
                            int row = c.getAddedSubList().getFirst().getRow();
                            int col = c.getAddedSubList().getFirst().getColumn() + 1;
                            Platform.runLater(() -> table.getSelectionModel().clearSelection());
                            Platform.runLater(() -> table.getSelectionModel().select(row, table.getColumns().get(col)));
                        }
                    }
                });
        rowNumbers.setCellFactory(rowNumFactory);
        rowNumbers.setCellValueFactory(cellData ->
                new SimpleStringProperty(Integer.toString(cellData.getValue().getRowColNumber())));
        rowNumbers.setPrefWidth(50);
        rowNumbers.setResizable(false);
        rowNumbers.setEditable(false);
        rowNumbers.setSortable(false);
        table.getColumns().add(rowNumbers);

        // Create the table columns dynamically
        for (int i = 1; i <= Math.max(spreadsheet != null ? spreadsheet.numColumns() : 0, INITIAL_COLUMN_NUM); i++) {
            newColumn();
        }

        // Populate the table with rows of the spreadsheet
        for (int row = 1; row <= Math.max(spreadsheet != null ? spreadsheet.numRows() : 0, INITIAL_ROW_NUM); row++) {
            newRow();
        }

        newColumnButton.setOnAction(event -> {newColumn();});
        newRowButton.setOnAction(event -> {newRow();});

        table.setItems(list);
        // add copy paste when cell isn't focused
        table.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN).match(keyEvent)) {
                copyBuffer = table.getSelectionModel().getSelectedItem().get(table.getSelectionModel().getSelectedIndex() + 1).getPlaintext();
                keyEvent.consume();
            } else if (new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN).match(keyEvent)
                && copyBuffer != null) {
                table.getSelectionModel().getSelectedItem().get(table.getSelectionModel().getSelectedIndex() + 1).updateCell(copyBuffer);
                keyEvent.consume();
                table.refresh();
            }
        });
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
                    sheetItem.setOnAction((event) -> {
                        setSpreadsheet(publisher, sheet);
                    });
                    publisherMenu.getItems().add(sheetItem);
                }
                openRecentMenu.getItems().add(publisherMenu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpreadsheet(String publisher, String sheet) {
        try {
            spreadsheet = spreadsheetManager.getSpreadsheet(publisher, sheet);
        } catch (APICallException e) {
            //todo probably display message to user instead
            throw new RuntimeException(e);
        }
        setCurrentDisplay(publisher, sheet);
        setupTable();
    }

    private void deleteSheet() {
        try {
            // Get the selected sheet from the "Open Recent" menu
            MenuItem selectedMenuItem = openRecentMenu.getItems().stream()
                    .filter(MenuItem.class::isInstance)
                    .map(MenuItem.class::cast)
                    .findFirst()
                    .orElse(null);

            if (selectedMenuItem == null) {
                System.out.println("No sheet selected to delete.");
                return;
            }

            String sheetName = selectedMenuItem.getText();

            // Get the publisher of the selected sheet
            Menu publisherMenu = (Menu) selectedMenuItem.getParentMenu();
            String publisher = publisherMenu.getText();

            // Send a request to delete the sheet from the server
            spreadsheetManager.deleteSpreadsheet(publisher, sheetName);

            // Refresh the "Open Recent" menu after deletion
            addItemsToOpenRecentMenu();

            // Optionally, you can update the UI to reflect the changes
        } catch (APICallException e) {
            e.printStackTrace();
            // Handle API call exception
        }
    }


    private void createSheet() {
        try {
            // Delete all existing sheets first
            deleteAllSheets();

            // Send a request to create a new sheet with the name "Sheet1"
            spreadsheetManager.createSpreadsheet("Sheet134134");

            // Refresh the "Open Recent" menu after creation
            addItemsToOpenRecentMenu();

            // Optionally, you can update the UI to reflect the changes
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    private void deleteAllSheets() {
        try {
            // Get the list of publishers
            List<String> publishers = spreadsheetManager.getPublishers();
            for (String publisher : publishers) {
                // Get the list of sheets for each publisher
                List<String> sheets = spreadsheetManager.getAvailableSheets(publisher);
                // Delete each sheet
                for (String sheet : sheets) {
                    spreadsheetManager.deleteSpreadsheet(publisher, sheet);
                }
            }
        } catch (APICallException e) {
            e.printStackTrace();
            // Handle API call exception
        }
    }


    private void setCurrentDisplay(String publisher, String sheet) {

    }

    /**
     * Add a new row to the table at the end of the rows.
     * @author Jackson Magas
     */
    private void newRow() {
        list.add(new SpreadsheetSliceView(spreadsheet, Direction.row, numRows + 1));
        ++numRows;
    }

    /**
     * Create a new column and add it to the table.
     * @author Jackson Magas
     */
    private void newColumn() {
        TableColumn<SpreadsheetSliceView, String> column = new TableColumn<>(Conversions.columnToString(numCols + 1));
        column.setPrefWidth(100);
        setupColumn(column, numCols + 1);
        table.getColumns().add(column);
        ++numCols;
    }

    /**
     * Set up a column of the spreadsheet.
     * Create a cell value factory which gets the data from the cell at that row/column.
     * Create a cell factory which sets each cell as an editable cell.
     * Set the callback so edits to cells in this column are reflected in the data.
     * @param column the column to set up
     * @param columnNumber the column number
     * @author Jackson Magas
     */
    private void setupColumn(TableColumn<SpreadsheetSliceView, String> column, int columnNumber) {
        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(columnNumber).getData()));
        column.setEditable(true);
        column.setCellFactory(tc -> {
            var cell = new EditingCell();
            // add copy and paste functionality when cell selected
            EventHandler<KeyEvent> copyPaste = keyEvent -> {
                if (new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN).match(keyEvent)) {
                    copyBuffer = list.get(cell.getIndex()).get(columnNumber).getPlaintext();
                    keyEvent.consume();
                } else if (new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN).match(keyEvent)
                    && copyBuffer != null) {
                    cell.commitEdit(copyBuffer);
                    keyEvent.consume();
                    table.refresh();
                }
            };
            cell.addEventFilter(KeyEvent.KEY_PRESSED, copyPaste);
            //cell.getTextField().addEventFilter(KeyEvent.KEY_PRESSED, copyPaste);
            return cell;
        });
            //TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        column.setOnEditCommit(event -> {
            try {
                event.getRowValue().get(columnNumber).updateCell(event.getNewValue());
            } catch (NullPointerException ignored) {
                // the event system ends up sending a null event every other time
            }
            table.refresh();
        });
    }
}
