package com.example.huskysheet;

import com.example.huskysheet.controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Application class for frontend.
 */
public class HelloApplication extends Application {
    private String userName;
    private String password;
    private String url;
    private String initialSheetName;
    private String initialSheetPublisher;

    /**
     * Start the GUI
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException if loading the fxml fails
     * @author Nikita Clark
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("HuskySheet");
        stage.setScene(scene);
        HelloController controller = fxmlLoader.getController();

        parseArgs();
        controller.init(url, userName, password, initialSheetPublisher, initialSheetName);

        stage.show();
    }

    /**
     * Parse the command line arguments
     * @author Jackson Magas
     */
    private void parseArgs() {
        var params = getParameters().getNamed();
        url = params.get("url");
        userName = params.get("name");
        password = params.get("password");
        initialSheetPublisher = params.get("publisher");
        initialSheetName = params.get("sheet");
    }

    public static void main(String[] args) {
        launch(args);
    }
}


