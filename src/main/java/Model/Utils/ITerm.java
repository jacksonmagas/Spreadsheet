package Model.Utils;

import java.util.List;

public interface ITerm {
    // get the result of evaluating the term as a string
    String getResult();
    
    // get raw text entered into the formula
    @Override
    String toString();

    /**
     * Recalculate the stored value of this term based on
     * updated values of cells it references
     */
    void recalculate();

    /**
     * Return the coordinates of all cells who's value this formula depends on
     * @return A list of dependency coordinates
     */
    List<Coordinate> dependencies();
}
