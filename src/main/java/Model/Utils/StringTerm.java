package Model.Utils;

import java.util.ArrayList;
import java.util.List;

public class StringTerm implements ITerm {
    String value;

    public StringTerm(String value) {
        this.value = value;
    }

    @Override
    public String getResult() {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        } else {
            return value;
        }
    }
    
    public String toString() {
        return value;
    }

    @Override
    public void recalculate() {
        // intentionally empty
    }

    @Override
    public List<Coordinate> dependencies() {
        return new ArrayList<Coordinate>();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ITerm && getResult().equals(((StringTerm) o).getResult());
    }
}
