package com.example.huskysheet;

import Model.CellFormat;
import Model.ICell;
import Model.Spreadsheet;

import Model.Utils.SpreadsheetSliceView;
import Model.Utils.SpreadsheetSliceView.Direction;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TableView<List<ICell>> table;

    // Define the number of rows and columns
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private Spreadsheet spreadsheet;

    ObservableList<List<ICell>> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO spreadsheet selection
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

    /**
     * Set up a column of the spreadsheet.
     * Create a cell value factory which gets the data from the cell at that row/column.
     * Create a cell factory which sets each cell as an editable cell/
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
