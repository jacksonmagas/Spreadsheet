package Model.Utils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class NumberTerm implements ITerm {
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
    public ResultType resultType() {
        return ResultType.number;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void recalculate() {
        // a number term doesn't change when recalculated
    }

    @Override
    public List<Coordinate> dependencies() {
        return List.of();
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
