package com.example.huskysheet.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabaseConnection {
  public static void main(String[] args) {
    Connection connection = null;
    try {
      connection = DatabaseUtil.getConnection();
      System.out.println("Connected to database successfully!");

      // Test query
      String testQuery = "SELECT VERSION()"; // SQL query to return the version of the MySQL server
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(testQuery);

      if (resultSet.next()) {
        System.out.println("Database version: " + resultSet.getString(1));
      }

    } catch (SQLException e) {
      System.err.println("Connection failed:" + e.getMessage());
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.err.println("Error closing connection " + ex.getMessage());
      }
    }
  }
}
