package com.example.huskysheet.model.Expressions;

public class CircularErrorTerm extends ErrorTerm {
    public CircularErrorTerm(String plaintext) {
        super(plaintext);
    }

    @Override
    public String getResult() {
        return "Circular reference detected";
    }
}
