module com.example.huskysheet {
    requires com.google.gson;
    requires java.net.http;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.web;
    requires spring.web;

    exports com.example.huskysheet to javafx.graphics;
    exports com.example.huskysheet.controller to javafx.fxml;
    opens com.example.huskysheet.controller to javafx.fxml;
    opens com.example.huskysheet.client to com.google.gson;
}

