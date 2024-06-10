package com.example.huskysheet.controller;

import com.example.huskysheet.client.Model.ICell;
import com.example.huskysheet.client.Model.Spreadsheet;

import com.example.huskysheet.client.Utils.Conversions;
import com.example.huskysheet.client.Utils.SpreadsheetSliceView;
import com.example.huskysheet.client.Utils.SpreadsheetSliceView.Direction;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TableView<SpreadsheetSliceView> table;
    @FXML
    private TableColumn<SpreadsheetSliceView, String> rowNumbers;
    @FXML
    private Button newColumnButton;
    @FXML
    private Button newRowButton;

    private String userName;
    private String password;
    private String url;

    // Define the number of rows and columns
    private static final int INITIAL_ROW_NUM = 8;
    private static final int INITIAL_COLUMN_NUM = 8;
    private int numRows = 0;
    private int numCols = 0;

    private Spreadsheet spreadsheet;

    ObservableList<SpreadsheetSliceView> list = FXCollections.observableArrayList();

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO spreadsheet selection
        spreadsheet = new Spreadsheet();

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
        for (int i = 1; i <= Math.max(spreadsheet.numColumns(), INITIAL_COLUMN_NUM); i++) {
            newColumn();
        }

        // Populate the table with rows of the spreadsheet
        for (int row = 1; row <= Math.max(spreadsheet.numRows(), INITIAL_ROW_NUM); row++) {
            newRow();
        }

        newColumnButton.setOnAction(event -> {newColumn();});
        newRowButton.setOnAction(event -> {newRow();});

        table.setItems(list);
    }

    private void newRow() {
        list.add(new SpreadsheetSliceView(spreadsheet, Direction.row, numRows + 1));
        ++numRows;
    }

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
     * Create a cell factory which sets each cell as an editable cell/
     * Set the callback for edits in this column to edit the cell
     * @param column the column to set up
     * @param columnNumber the column number
     */
    private void setupColumn(TableColumn<SpreadsheetSliceView, String> column, int columnNumber) {
        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(columnNumber).getData()));
        column.setEditable(true);
        column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        column.setOnEditCommit(event -> {
            event.getRowValue().get(columnNumber + 1).updateCell(event.getNewValue());
            table.refresh();
        });
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
