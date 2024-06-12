package com.example.huskysheet.client;

public class APICallException extends Exception {

    public APICallException(Exception e) {
        super(e);
    }

    public APICallException(String message) {
        super(message);
    }
}
