package com.example.huskysheet.client.Expressions;

/**
 * Represents specifically an error caused by a cell which depends on its value to calculate its value.
 * @author Jackson Magas
 */
public class CircularErrorTerm extends ErrorTerm {
    public CircularErrorTerm(String plaintext) {
        super(plaintext);
    }

    @Override
    public String getResult() {
        return "Circular reference detected";
    }
}
