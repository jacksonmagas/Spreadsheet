package Model.Utils;

import java.util.List;

public interface ITerm {
    // get the result of evaluating the term as a string
    String getResult();
    
    // get raw text entered into the formula
    @Override
    String toString();

    /**
     * Get a list of all the references required to calculate this term.
     * @return A list containing all references in this term
     */
    List<Ref> dependencies();
}
