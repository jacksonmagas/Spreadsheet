package com.example.huskysheet.controller;

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
 * This class creates the Registration for a User. It prompts a user to create a Username
 * and password. Once they press okay they are added to the database. They are then
 * brought back to the main login screen and asked to login with their new credetials.
 *
 * @author Katie w
 */

public class RegisterController {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  private final AuthenticationService authenticationService = new AuthenticationService();

  @FXML
  private void handleRegisterButtonAction() {
    System.out.println("Register button action triggered");
    String username = usernameField.getText();
    String password = passwordField.getText();

    if (authenticationService.register(username, password)) {
      showAlert("Registration Successful", "You can now log in with your credentials.");
      loadLoginView();
    } else {
      showAlert("Registration Failed", "User may already exist or an error occurred.");
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void loadLoginView() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/huskysheet/login-view.fxml"));
      Stage stage = (Stage) usernameField.getScene().getWindow();
      Scene scene = new Scene(loader.load());
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

