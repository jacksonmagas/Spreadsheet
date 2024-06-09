<<<<<<<< HEAD:src/main/java/com/example/huskysheet/model/Expressions/CircularErrorTerm.java
package com.example.huskysheet.model.Expressions;
========
package Client.Model.Expressions;
>>>>>>>> UIIntegration:src/main/java/Client/Model/Expressions/CircularErrorTerm.java

public class CircularErrorTerm extends ErrorTerm {
    public CircularErrorTerm(String plaintext) {
        super(plaintext);
    }

    @Override
    public String getResult() {
        return "Circular reference detected";
    }
}
