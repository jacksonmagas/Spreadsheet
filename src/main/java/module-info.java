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

  exports com.example.huskysheet.model;
  opens com.example.huskysheet.model to javafx.fxml, org.mockito, spring.core;

  exports com.example.huskysheet.utils;
  opens com.example.huskysheet.utils to javafx.fxml, spring.core;

  exports com.example.huskysheet.api.Server;
  opens com.example.huskysheet.api.Server to org.mockito, spring.core;

  exports com.example.huskysheet.config;
  opens com.example.huskysheet.config to javafx.fxml, spring.core;

  exports com.example.huskysheet.controller;
  opens com.example.huskysheet.controller to javafx.fxml, org.mockito, spring.core;

  exports com.example.huskysheet.service;
  opens com.example.huskysheet.service to javafx.fxml, spring.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires java.net.http;
    requires com.google.gson;

    opens com.example.huskysheet to javafx.fxml;
    exports com.example.huskysheet;
    exports Client.Model;
    opens Client.Model to javafx.fxml;
    exports Client.Model.Utils;
    opens Client.Model.Utils to javafx.fxml;
    opens Server.api to org.mockito;
    opens Server.model to org.mockito;
    exports Client.Model.Expressions;
    opens Client.Model.Expressions to javafx.fxml;
}

