package Model.Utils.Function;

import java.util.ArrayList;

public class ConcreteFunction implements IFunction {

    ArrayList<IFunctionElement> elements;
    ArrayList<FunctionOperation> operations;

    public ConcreteFunction() {
        this.elements = new ArrayList<>();
        this.operations = new ArrayList<>();
    }

    public ConcreteFunction(String s) {
        String[] tokens = s.split("\\s+");
        this.proccessTokens(tokens);
    }

    public ConcreteFunction(ArrayList<IFunctionElement> elements, ArrayList<FunctionOperation> operations) {
        if (elements.size() != operations.size() - 1) {
            throw new IllegalArgumentException("Number of elements does not match number of operations in function.");
        } else {
            this.elements = elements;
            this.operations = operations;
        }
    }

    @Override
    public IFunctionElement evaluate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }

    private void proccessTokens(String[] tokens) {
        // TODO
    }
    
}
