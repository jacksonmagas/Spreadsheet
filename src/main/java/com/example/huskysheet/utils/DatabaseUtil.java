<<<<<<<< HEAD:src/main/java/com/example/huskysheet/utils/DatabaseUtil.java
package com.example.huskysheet.utils;
========
package Client.Model.Utils;
>>>>>>>> UIIntegration:src/main/java/Client/Model/Utils/DatabaseUtil.java
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
