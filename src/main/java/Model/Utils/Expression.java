package Model.Utils;

import java.util.List;

public abstract class Expression implements ITerm {
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
