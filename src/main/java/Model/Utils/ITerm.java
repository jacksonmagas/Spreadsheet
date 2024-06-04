package Model.Utils;

import java.util.List;

public interface ITerm {
    // get the result of evaluating the term as a string
    String getResult();
    
    // get raw text entered into the formula
    @Override
    String toString();

    enum ResultType {
        string, number, empty, error
    }

    /**
     * Get the result type of this term, either string, number, or empty
     */
    ResultType resultType();

    /**
     * Get whether this term is empty.
     * An empty term is the empty term or a reference to the empty term.
     * Jackson Magas
     */
    boolean isEmpty();

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
