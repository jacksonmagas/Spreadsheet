module com.example.huskysheet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires java.ws.rs;
    requires jersey.server;
    requires jersey.container.servlet.core;
    requires javax.servlet.api;

    opens com.example.huskysheet to javafx.fxml;
    exports com.example.huskysheet;
    exports Model;
    opens Model to javafx.fxml;
    exports Model.Utils;
    opens Model.Utils to javafx.fxml;
    opens Server.api to org.mockito;
    opens Server.model to org.mockito;
}
