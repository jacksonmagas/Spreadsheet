package Model.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorTerm errorTerm = (ErrorTerm) o;
        return Objects.equals(plaintext, errorTerm.plaintext);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(plaintext);
    }
}
