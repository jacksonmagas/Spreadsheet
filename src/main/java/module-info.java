module com.example.huskysheet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.huskysheet to javafx.fxml;
    exports com.example.huskysheet;
}