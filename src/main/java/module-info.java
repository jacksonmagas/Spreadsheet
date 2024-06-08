module com.example.huskysheet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires java.net.http;
    requires com.google.gson;

    opens com.example.huskysheet to javafx.fxml;
    exports com.example.huskysheet;
    exports Client.Model;
    opens Client.Model to javafx.fxml;
    exports Client.Model.Utils;
    opens Client.Model.Utils to javafx.fxml;
    opens Server.api to org.mockito;
    opens Server.model to org.mockito;
    exports Client.Model.Expressions;
    opens Client.Model.Expressions to javafx.fxml;
}