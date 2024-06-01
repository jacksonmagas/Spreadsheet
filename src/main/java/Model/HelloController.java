package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TableView<CellFormat> table;
    @FXML
    private TableColumn<CellFormat, Integer> id;
    @FXML
    private TableColumn<CellFormat, String> name;
    @FXML
    private TableColumn<CellFormat, String> lname;
    @FXML
    private TableColumn<CellFormat, String> gmail;
    @FXML
    private TableColumn<CellFormat, String> yahoo;
    @FXML
    private TableColumn<CellFormat, String> phone;


    ObservableList<CellFormat> list = FXCollections.observableArrayList(
            new CellFormat(1,  "John", "Doe", "john.doe@gmail.com", "john_d@yahoo.com", "1234567890"),
            new CellFormat(2,   "Jane", "Doe", "jane.doe@gmail.com", "jane_d@yahoo.com", "2345678901"),
            new CellFormat(3,   "Alice", "Smith", "alice.smith@gmail.com", "alice_s@yahoo.com", "3456789012"),
            new CellFormat(4,   "Bob", "Brown", "bob.brown@gmail.com", "bob_b@yahoo.com", "4567890123"),
            new CellFormat(5,  "Carol", "Jones", "carol.jones@gmail.com", "carol_j@yahoo.com", "5678901234"),
            new CellFormat(6,  "Dave", "Wilson", "dave.wilson@gmail.com", "dave_w@yahoo.com", "6789012345"),
            new CellFormat(7,  "Eve", "Taylor", "eve.taylor@gmail.com", "eve_t@yahoo.com", "7890123456"),
            new CellFormat(8,  "Frank", "Moore", "frank.moore@gmail.com", "frank_m@yahoo.com", "8901234567"),
            new CellFormat(9,  "Grace", "Lee", "grace.lee@gmail.com", "grace_l@yahoo.com", "9012345678"),
            new CellFormat(10,  "Henry", "Davis", "henry.davis@gmail.com", "henry_d@yahoo.com", "0123456789")
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        id.setCellValueFactory(new PropertyValueFactory<CellFormat, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<CellFormat, String>("name"));
        lname.setCellValueFactory(new PropertyValueFactory<CellFormat, String>("lname"));
        gmail.setCellValueFactory(new PropertyValueFactory<CellFormat, String>("gmail"));
        yahoo.setCellValueFactory(new PropertyValueFactory<CellFormat, String>("yahoo"));
        phone.setCellValueFactory(new PropertyValueFactory<CellFormat, String>("phone"));


        table.setItems(list);

  }


}
