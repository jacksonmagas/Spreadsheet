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

    /**
     * Create a new number term from a double
     * @param value the value of the new term to create
     */
    public NumberTerm(double value) {
        super(format(value));
        this.value = value;
    }

    /**
     * Format the number term
     * @param value the double to format
     * @return the formatted double
     * @author Jackson Magas
     */
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
