package com.example.huskysheet.client.Expressions;

public class CircularErrorTerm extends ErrorTerm {
    public CircularErrorTerm(String plaintext) {
        super(plaintext);
    }

    @Override
    public String getResult() {
        return "Circular reference detected";
    }
}
