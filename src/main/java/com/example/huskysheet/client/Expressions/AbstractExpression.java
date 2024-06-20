package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Utils.Coordinate;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Represents an equation which can depend on other terms and can recalculate its results.
 * @author Jackson Magas
 */
public abstract class AbstractExpression implements ITerm {
    protected static final String DIV_ZERO = "#DIV/0!";
    protected static final String VALUE_ERROR = "#VALUE!";

    String value;
    String plaintext;
    List<Coordinate> dependencies;

    public AbstractExpression(String plaintext) {
        this.plaintext = plaintext;
    }

    @Override
    public String getResult() {
        return value;
    }

    @Override
    public String toString() {
        return plaintext;
    }

    /**
     * Get a list of the multiple results of this term
     *
     * @return a list of terms that are the result of the results
     */
    @Override
    public List<ITerm> getMultipleResults() {
        return List.of();
    }

    /**
     * Format numbers consistently
     * @param value the double to format
     * @return a formatted string of the number
     * @author Jackson Magas
     */
    protected String format(double value) {
        return new DecimalFormat("#.################").format(value);
    }


}
