<<<<<<<< HEAD:src/main/java/com/example/huskysheet/model/Expressions/NumberTerm.java
package com.example.huskysheet.model.Expressions;

import com.example.huskysheet.utils.Coordinate;
========
package Client.Model.Expressions;

import Client.Model.Utils.Coordinate;
>>>>>>>> UIIntegration:src/main/java/Client/Model/Expressions/NumberTerm.java
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class NumberTerm extends AbstractValueTerm {
    private final double value;

    public NumberTerm(double value) {
        this.value = value;
    }

    @Override
    public String getResult() {
        return new DecimalFormat("#.################").format(value);
    }

    public String toString() {
        return Double.toString(value);
    }

    @Override
    public List<Coordinate> references() {
        return super.references();
    }

    @Override
    public ResultType resultType() {
        return ResultType.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NumberTerm that = (NumberTerm) o;
        return Math.abs(this.value - that.value) < 0.000001;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
