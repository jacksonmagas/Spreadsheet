package com.example.huskysheet;

import com.example.huskysheet.controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private String userName;
    private String password;
    private String url;
    private String initialSheetName;
    private String initialSheetPublisher;

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


