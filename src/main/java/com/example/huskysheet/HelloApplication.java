package com.example.huskysheet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file from the classpath using getResourceAsStream
        InputStream fxmlStream = getClass().getResourceAsStream("hello-view.fxml");
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(fxmlStream);

        Scene scene = new Scene(root);
        stage.setTitle("HuskySheet");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}