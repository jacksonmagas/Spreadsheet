package com.example.huskysheet.client.Expressions;

import com.example.huskysheet.client.Utils.Coordinate;
import java.util.List;

/**
 * Interface representing a term in an equation or a cell.
 * A term can be a simple value holder such as a string or number, or a more complex term with
 * references to cells and/or subterms combined.
 */
public interface ITerm {
    /**
     * Get the result of this term as a string.
     * @author Jackson Magas
     * @return the result of evaluating this term
     */
    String getResult();

    /**
     * Get the exact text of the term as a string
     * @return the text for the term to evaluate
     * @author Jackson Magas
     */
    @Override
    String toString();

    /**
     * Get a list of the multiple results of this term
     * @return a list of terms that are the result of the results
     */
    List<ITerm> getMultipleResults();

    /**
     * Gets a list of all cells referenced directly by this term.
     * For example =SUM($A1, $A2, $A3) would return List.of($A1, $A2, $A3)
     * @return the list of references
     */
    List<Coordinate> references();

    /**
     * Enum representing the possible types for the result of an ITerm
     */
    enum ResultType {
        string, number, empty, error, range
    }

    /**
     * Get the result type of this term, either string, number, or empty
     */
    ResultType resultType();

    /**
     * Get whether this term is empty.
     * An empty term is the empty term or a reference to the empty term.
     * @author Jackson Magas
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
