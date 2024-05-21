import java.awt.*;

import Client.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main {
  public static void main(String[] args) {
    System.out.println("Welcome to Husksheets!");

    GUI gui = new GUI();
    gui.start();

    Client client = new Client();
    // client.initialize();

    Server server = new Server();
    server.start();
  }
}
