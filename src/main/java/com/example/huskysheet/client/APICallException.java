package com.example.huskysheet.client;

/**
 * General exception for all API call issues whether they are connection issues or format issues.
 * @author Jackson Magas
 */
public class APICallException extends Exception {

    public APICallException(Exception e) {
        super(e);
    }

    public APICallException(String message) {
        super(message);
    }
}
