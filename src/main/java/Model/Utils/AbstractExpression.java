package Model.Utils;

import java.util.List;

public abstract class AbstractExpression implements ITerm {

    static final String VALUE_ERROR = "#VALUE!";
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
}
