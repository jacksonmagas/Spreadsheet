module com.example.huskysheet {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires java.datatransfer;
  requires java.desktop;
  requires java.ws.rs;
  requires jersey.server;
  requires jersey.container.servlet.core;
  requires javax.servlet.api;
  requires spring.web;
  requires spring.security.core;
  requires jdk.jdi;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.core;
  requires spring.context;
  requires spring.security.config;
  requires spring.security.web;
  requires spring.beans; // Add this line to resolve IllegalAccessError

  opens com.example.huskysheet to javafx.fxml, spring.core;
  exports com.example.huskysheet;

  opens com.example.huskysheet.client to javafx.fxml, spring.core;
  requires com.google.gson;
  requires java.net.http;

  exports com.example.huskysheet.model;
  opens com.example.huskysheet.model to javafx.fxml, org.mockito, spring.core;

  exports com.example.huskysheet.api.Server;
  opens com.example.huskysheet.api.Server to org.mockito, spring.core;

  exports com.example.huskysheet.config;
  opens com.example.huskysheet.config to javafx.fxml, spring.core;

  exports com.example.huskysheet.controller;
  opens com.example.huskysheet.controller to javafx.fxml, org.mockito, spring.core;

    exports com.example.huskysheet.client;
    exports com.example.huskysheet.client.Utils;
    opens com.example.huskysheet.client.Utils to javafx.fxml;
    exports com.example.huskysheet.client.Expressions;
    opens com.example.huskysheet.client.Expressions to javafx.fxml;
  exports com.example.huskysheet.client.Model;
  opens com.example.huskysheet.client.Model to javafx.fxml, spring.core;
}

