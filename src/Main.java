import Client.Client;

public class Main {
  public static void main(String[] args) {
    System.out.println("Welcome to Husksheets!");

    GUI gui = new GUI();
    gui.start();

    Client client = new Client();
    client.initialize();

    Server server = new Server();
    server.start();
  }
}
