package Client.Model.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
  private static final String URL = "jdbc:mysql://localhost:3306/spreadsheetProject";
  private static final String USER = "root";
  private static final String PASSWORD = "Julia2003!";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
