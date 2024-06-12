
module com.example.huskysheet {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires java.datatransfer;
  requires java.desktop;
  requires spring.web;
  requires spring.security.core;
  requires jdk.jdi;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.core;
  requires spring.context;
  requires spring.security.config;
  requires spring.security.web;
  requires spring.beans;
  requires java.net.http;
  requires com.google.gson;
  requires javafx.base;
  requires jakarta.persistence;


  opens com.example.huskysheet to javafx.fxml, spring.core;
  exports com.example.huskysheet;

  opens com.example.huskysheet.client to com.google.gson;


  exports com.example.huskysheet.model;
  opens com.example.huskysheet.model to javafx.fxml, org.mockito, spring.core;


  exports com.example.huskysheet.api.Server;
  opens com.example.huskysheet.api.Server to org.mockito, spring.core;


  exports com.example.huskysheet.config;
  opens com.example.huskysheet.config to javafx.fxml, spring.core;


  exports com.example.huskysheet.controller;
  opens com.example.huskysheet.controller to javafx.fxml, org.mockito, spring.core;
}
