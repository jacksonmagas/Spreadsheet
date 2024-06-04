package Model.Expressions;

import Model.Utils.Coordinate;
import java.util.List;
import java.util.Objects;

public class ErrorTerm extends AbstractValueTerm {
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
    public List<Coordinate> references() {
        return super.references();
    }

    @Override
    public ResultType resultType() {
        return ResultType.error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return this.isEmpty();
        }
        ErrorTerm errorTerm = (ErrorTerm) o;
        return Objects.equals(plaintext, errorTerm.plaintext);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(plaintext);
    }
}
