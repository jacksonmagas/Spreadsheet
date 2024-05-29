package Model.Utils;

import java.awt.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXTest extends Application {
  @Override
  public void start(Stage primaryStage) {
    java.awt.Label label = new Label("Hello, JavaFX!");
   // Scene scene = new Scene(label, 400, 200);
   // primaryStage.setScene(scene);
    primaryStage.setTitle("JavaFX Test");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
