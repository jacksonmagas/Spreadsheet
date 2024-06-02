module com.example.huskysheet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;


    opens com.example.huskysheet to javafx.fxml;
    exports com.example.huskysheet;
<<<<<<< HEAD
=======
    exports Model;
    opens Model to javafx.fxml;
    exports Model.Utils;
    opens Model.Utils to javafx.fxml;
    opens Server.api to org.mockito;
    opens Server.model to org.mockito;
>>>>>>> 88421949269ee9c6eb0de761fbb815b26a007e27
}