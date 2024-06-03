package Model.Utils;


import java.util.ArrayList;
import java.util.List;

public class StringTerm implements ITerm {
    String value;

    public StringTerm(String value) {
        this.value = value;
    }

    /**
     * The result of a string term is what to display for this term.
     * Outer quotes are removed for the display.
     * Jackson Magas
     * @return the value of this term with outer quotes removed
     */
    @Override
    public String getResult() {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        } else {
            return value;
        }
    }

    // Jackson Magas
    public String toString() {
        return value;
    }

    // Jackson Magas
    @Override
    public void recalculate() {
        // intentionally empty value terms do not need to recalculate
    }

    // Jackson Magas
    @Override
    public List<Coordinate> dependencies() {
        // value terms have no dependencies
        return new ArrayList<Coordinate>();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ITerm && getResult().equals(((ITerm) o).getResult());
    }
}
