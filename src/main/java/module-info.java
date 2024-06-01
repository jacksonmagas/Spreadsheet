module com.example.huskysheet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires jdk.jdi;


    opens com.example.huskysheet to javafx.fxml;
    exports com.example.huskysheet;
    exports Model;
    opens Model to javafx.fxml;
}