package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TableView<CellFormat> table;
    @FXML
    private TableColumn<CellFormat, Integer> c1;
    @FXML
    private TableColumn<CellFormat, Integer> c2;
    @FXML
    private TableColumn<CellFormat, Integer> c3;
    @FXML
    private TableColumn<CellFormat, Integer> c4;
    @FXML
    private TableColumn<CellFormat, Integer> c5;
    @FXML
    private TableColumn<CellFormat, Integer> c6;
    @FXML
    private TableColumn<CellFormat, Integer> c7;
    @FXML
    private TableColumn<CellFormat, Integer> c8;


    ObservableList<CellFormat> list = FXCollections.observableArrayList(
            new CellFormat(1, 2, 3, 4, 5, 6, 7, 8), // Numbers 1 to 8
            new CellFormat(9, 10, 11, 12, 13, 14, 15, 16), // Numbers 9 to 16
            new CellFormat(17, 18, 19, 20, 21, 22, 23, 24), // Numbers 17 to 24
            new CellFormat(25, 26, 27, 28, 29, 30, 31, 32), // Numbers 25 to 32
            new CellFormat(33, 34, 35, 36, 37, 38, 39, 40), // Numbers 33 to 40
            new CellFormat(41, 42, 43, 44, 45, 46, 47, 48), // Numbers 41 to 48
            new CellFormat(49, 50, 51, 52, 53, 54, 55, 56), // Numbers 49 to 56
            new CellFormat(57, 58, 59, 60, 61, 62, 63, 64), // Numbers 57 to 64
            new CellFormat(65, 66, 67, 68, 69, 70, 71, 72), // Numbers 65 to 72
            new CellFormat(73, 74, 75, 76, 77, 78, 79, 80),  // Numbers 73 to 80
             new CellFormat(73, 74, 75, 76, 77, 78, 79, 80),  // Numbers 73 to 80
     new CellFormat(73, 74, 75, 76, 77, 78, 79, 80),  // Numbers 73 to 80
     new CellFormat(73, 74, 75, 76, 77, 78, 79, 80),  // Numbers 73 to 80
     new CellFormat(73, 74, 75, 76, 77, 78, 79, 80)  // Numbers 73 to 80

    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        table.setEditable(true);

        table.getSelectionModel().setCellSelectionEnabled(true);

        c1.setCellValueFactory(new PropertyValueFactory<CellFormat, Integer>("c1"));
        c2.setCellValueFactory(new PropertyValueFactory<CellFormat, Integer>("c2"));
        c3.setCellValueFactory(new PropertyValueFactory<CellFormat, Integer>("c3"));
        c4.setCellValueFactory(new PropertyValueFactory<CellFormat, Integer>("c4"));
        c5.setCellValueFactory(new PropertyValueFactory<CellFormat, Integer>("c5"));
        c6.setCellValueFactory(new PropertyValueFactory<CellFormat, Integer>("c6"));
        c7.setCellValueFactory(new PropertyValueFactory<CellFormat, Integer>("c7"));
        c8.setCellValueFactory(new PropertyValueFactory<CellFormat, Integer>("c8"));

        // Setup columns
        setupColumn(c1, "c1");
        setupColumn(c2, "c2");
        setupColumn(c3, "c3");
        setupColumn(c4, "c4");
        setupColumn(c5, "c5");
        setupColumn(c6, "c6");
        setupColumn(c7, "c7");
        setupColumn(c8, "c8");




        table.setItems(list);
    }

    private void setupColumn(TableColumn<CellFormat, Integer> column, String propertyName) {
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        column.setOnEditCommit(event -> {
            CellFormat cell = event.getRowValue();
            switch (propertyName) {
                case "c1": cell.setC1(event.getNewValue()); break;
                case "c2": cell.setC2(event.getNewValue()); break;
                case "c3": cell.setC3(event.getNewValue()); break;
                case "c4": cell.setC4(event.getNewValue()); break;
                case "c5": cell.setC5(event.getNewValue()); break;
                case "c6": cell.setC6(event.getNewValue()); break;
                case "c7": cell.setC7(event.getNewValue()); break;
                case "c8": cell.setC8(event.getNewValue()); break;
            }
            table.refresh(); // Optionally refresh if immediate visual update is necessary
        });
    }
}
