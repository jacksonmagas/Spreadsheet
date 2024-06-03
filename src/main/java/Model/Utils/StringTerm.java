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
        String result = withoutEscapes(value);
        if (result.startsWith("\"") && result.endsWith("\"")) {
            return result.substring(1, result.length() - 1);
        } else {
            return result;
        }
    }

    // remove escaped slashes from escapes entered by the user
    // Jackson Magas
    private String withoutEscapes(String value) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) != '\\') {
                result.append(value.charAt(i));
            }
        }
        return result.toString();
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
