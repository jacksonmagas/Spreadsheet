package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Utils.Coordinate;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

/**
 * Represents an equation term which is a double
 * @author Jackson Magas
 */
public class NumberTerm extends AbstractValueTerm {
    private final double value;

    public NumberTerm(double value) {
        super(format(value));
        this.value = value;
    }

    private static String format(double value) {
        return new DecimalFormat("#.################").format(value);
    }

    @Override
    public String getResult() {
        return format(value);
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
