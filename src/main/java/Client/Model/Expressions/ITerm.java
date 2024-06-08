package Client.Model.Expressions;

import Client.Model.Utils.Coordinate;
import java.util.List;

public interface ITerm {
    // get the result of evaluating the term as a string
    String getResult();
    
    // get raw text entered into the formula
    @Override
    String toString();


    /**
     * Gets a list of all cells referenced directly by this term.
     * For example =SUM($A1, $A2, $A3) would return List.of($A1, $A2, $A3)
     * @return the list of references
     */
    List<Coordinate> references();

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
     * Determine if this term depends on the value of the cell at cellLoc
     * @param cellLoc the location of the cell to check for
     * @return true if this cell depends on the given cell
     */
    boolean dependsOn(Coordinate cellLoc);
}
