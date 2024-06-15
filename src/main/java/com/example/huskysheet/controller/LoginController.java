package com.example.huskysheet.controller;

import com.example.huskysheet.HelloApplication;
import com.example.huskysheet.model.AuthenticationService;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class creates the Login for Users. It asks for a username and password and
 * matches it with data in the system. Any user who has registered is a valid user and
 * is able to log in with those credentials. Login Controller also makes sure to address
 * if username or password is incorrect.
 *
 * @author Katie w
 */

public class LoginController {

  @FXML
  private TextField usernameField;

  private HelloApplication helloApplication;

  @FXML
  private PasswordField passwordField;

  private AuthenticationService authenticationService = new AuthenticationService();

  @FXML
  private void loginUser() {
    String username = usernameField.getText();
    String password = passwordField.getText();

    boolean authenticated = authenticationService.authenticate(username, password);

    if (authenticated) {
      showAlert("Login Successful", "You have successfully logged in.");
      loadHelloView();
    } else {
      showAlert("Login Failed", "Invalid username or password. Please try again.");
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public void setHelloApplication(HelloApplication helloApplication) {
    this.helloApplication = helloApplication;
  }

  private void loadHelloView() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/huskysheet/hello-view.fxml"));
      Stage stage = (Stage) usernameField.getScene().getWindow();
      Scene scene = new Scene(loader.load());
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
