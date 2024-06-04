package Model.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractExpression implements ITerm {
    protected static final String DIV_ZERO = "#DIV/0!";
    protected static final String VALUE_ERROR = "#VALUE!";

    String value;
    String plaintext;
    List<Coordinate> dependencies;

    @Override
    public String getResult() {
        return value;
    }

    @Override
    public String toString() {
        return plaintext;
    }

    @Override
    public List<Coordinate> dependencies() {
        return dependencies;
    }

    protected String format(double value) {
        return new DecimalFormat("#.################").format(value);
    }
}
