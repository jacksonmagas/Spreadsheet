module com.example.huskysheet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;


    opens com.example.huskysheet to javafx.fxml;
    exports com.example.huskysheet;
}