package com.example.huskysheet.controller;

import com.example.huskysheet.HelloApplication;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This class creates the intial screen for a User when they launch the gui. It
 * gives users the option to Login or Register. If a User presses Login, it will bring
 * them to the Login page using the LoginController. Or it will bring them to the
 * Register Page using the registerController.
 *
 * @author Katie w
 */

public class InitialController {

  private HelloApplication helloApplication;

  @FXML
  private void handleLoginButtonAction(ActionEvent event) {
    System.out.println("Login button clicked");
    try {
      loadView(event, "/com/example/huskysheet/login-view.fxml");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleRegisterButtonAction(ActionEvent event) {
    System.out.println("Register button clicked");
    try {
      loadView(event, "/com/example/huskysheet/register-view.fxml");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadView(ActionEvent event, String fxmlFile) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void setHelloApplication(HelloApplication helloApplication) {
    this.helloApplication = helloApplication;
  }
}
