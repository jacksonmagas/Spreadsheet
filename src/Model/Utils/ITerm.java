
public interface ITerm {
    // get the result of evaluating the term as a string
    String getResult();
    
    // get raw text entered into the formula
    @Override
    String toString();
}
