package com.example.huskysheet;

import com.example.huskysheet.controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static String[] appArgs;
    private String userName;
    private String password;
    private String url;
    private RunType runType;

    private enum RunType {
        server, client, both
    }

    @Override
    public void start(Stage stage) throws IOException {
        switch (runType) {
            case server -> {
                HuskySheetApplication.main(appArgs);
                return;
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("HuskySheet");
        stage.setScene(scene);
        HelloController controller = fxmlLoader.getController();

        parseArgs();
        controller.setUrl(url);
        controller.setUserName(userName);
        controller.setPassword(password);
        controller.init();

        stage.show();
    }


    private void parseArgs() {
        var params = getParameters().getNamed();
        url = params.get("url");
        userName = params.get("name");
        password = params.get("password");
        runType = RunType.valueOf(params.getOrDefault("mode", "both").toLowerCase());
    }

    public static void main(String[] args) {
        appArgs = args;
        launch(args);
    }
}


