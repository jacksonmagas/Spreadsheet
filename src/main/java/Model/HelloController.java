package Model;

import Model.Utils.Coordinate;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TableView<CellFormat> table;
    @FXML
    private TableColumn<CellFormat, String> c1;
    @FXML
    private TableColumn<CellFormat, String> c2;
    @FXML
    private TableColumn<CellFormat, String> c3;
    @FXML
    private TableColumn<CellFormat, String> c4;
    @FXML
    private TableColumn<CellFormat, String> c5;
    @FXML
    private TableColumn<CellFormat, String> c6;
    @FXML
    private TableColumn<CellFormat, String> c7;
    @FXML
    private TableColumn<CellFormat, String> c8;

    // Define the number of rows and columns
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;

    ObservableList<CellFormat> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setEditable(true);

        table.getSelectionModel().setCellSelectionEnabled(true);

        // Create the table columns dynamically
        for (int i = 0; i < COLUMNS; i++) {
            TableColumn<CellFormat, String> column = new TableColumn<>("Column " + (i + 1));
            int columnIndex = i;
            column.setCellValueFactory(cellData -> {
                Coordinate coordinate = cellData.getValue().getCoordinate();
                return new SimpleStringProperty(cellData.getValue().getValue());
            });
            setupColumn(column);
            table.getColumns().add(column);
        }

        // Populate the table with CellFormat objects
        int cellValue = 1;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                list.add(new CellFormat(String.valueOf(cellValue++), new Coordinate(row, col)));
            }
        }

        table.setItems(list);

        // Make all columns editable
        makeAllCellsEditable();
    }


    private void setupColumn(TableColumn<CellFormat, String> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        column.setOnEditCommit(event -> {
            CellFormat cell = event.getRowValue();
            cell.setValue(event.getNewValue());
            table.refresh();
        });
    }

    private void makeAllCellsEditable() {
        for (TableColumn<CellFormat, ?> column : table.getColumns()) {
            makeCellsEditable((TableColumn<CellFormat, String>) column);
        }
    }

    private void makeCellsEditable(TableColumn<CellFormat, String> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        column.setOnEditCommit(event -> {
            CellFormat cell = event.getRowValue();
            cell.setValue(event.getNewValue());
            // Update the cell value in the TableView
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setValue(event.getNewValue());
        });
    }

}
