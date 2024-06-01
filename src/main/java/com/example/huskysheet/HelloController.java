package com.example.huskysheet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.ICell;
import Model.CellFormat;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<ICell> table;
    @FXML
    private TableColumn<ICell, String> C1; // Update the type according to the property type of ICell

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configure columns
        setupColumns();

        // Load and set data to the table
        loadData();
    }

    private void setupColumns() {
        // Assuming ICell has a method getStringProperty()
        C1.setCellValueFactory(new PropertyValueFactory<>("stringProperty")); // "stringProperty" should be a valid property name in ICell
    }

    private void loadData() {
        // Example data
        ObservableList<ICell> data = FXCollections.observableArrayList(
            /*    new YourCellImplementation("Value1"),
                new YourCellImplementation("Value2"),
                new YourCellImplementation("Value3")*/
        );

        table.setItems(data);
    }
}