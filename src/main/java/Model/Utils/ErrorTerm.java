package Model.Utils;

import java.util.ArrayList;
import java.util.List;

public class ErrorTerm implements ITerm {
    private final String plaintext;

    public ErrorTerm(String plaintext) {
        this.plaintext = plaintext;
    }

    @Override
    public String getResult() {
        return "Error invalid input.";
    }

    @Override
    public String toString() {
        return plaintext;
    }

    @Override
    public void recalculate() {

    }

    @Override
    public List<Coordinate> dependencies() {
        return new ArrayList<>();
    }
}
