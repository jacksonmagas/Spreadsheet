package Model.Utils;

import java.util.List;

public class NumberTerm implements ITerm {
    private final double value;

    public NumberTerm(double value) {
        this.value = value;
    }

    @Override
    public String getResult() {
        return Double.toString(value);
    }

    public String toString() {
        return Double.toString(value);
    }

    @Override
    public void recalculate() {

    }

    @Override
    public List<Coordinate> dependencies() {
        return List.of();
    }

}
