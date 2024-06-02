package Model.Utils;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.html.HTML.Tag.HEAD;

>>>>>>> 88421949269ee9c6eb0de761fbb815b26a007e27
public class StringTerm implements ITerm {

    @Override
    public String getResult() {
<<<<<<< HEAD
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResult'");
    }
    
    public String toString() {
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
=======
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
>>>>>>> 88421949269ee9c6eb0de761fbb815b26a007e27
    }
}
