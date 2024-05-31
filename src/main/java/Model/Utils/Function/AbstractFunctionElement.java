package Model.Utils.Function;

import Model.Utils.IRef;

public abstract class AbstractFunctionElement implements IFunctionElement {
    private static String IllegalOperationMessage(String op) {
        return "Operation \'" + op + "\' not supported for type";
    }

    @Override
    public IFunctionElement add(IFunctionElement e) {
        throw new UnsupportedOperationException(IllegalOperationMessage("add"));
    }

    @Override
    public IFunctionElement subtract(IFunctionElement e) {
        throw new UnsupportedOperationException(IllegalOperationMessage("subtract"));
    }

    @Override
    public IFunctionElement multiply(IFunctionElement e) {
        throw new UnsupportedOperationException(IllegalOperationMessage("multiply"));
    }

    @Override
    public IFunctionElement divide(IFunctionElement e) {
        throw new UnsupportedOperationException(IllegalOperationMessage("divide"));
    }

    @Override
    public boolean lessThan(IFunctionElement e) {
        throw new UnsupportedOperationException(IllegalOperationMessage("lessThan"));
    }

    @Override
    public boolean greaterThan(IFunctionElement e) {
        throw new UnsupportedOperationException(IllegalOperationMessage("greaterThan"));
    }

    @Override
    public boolean notEqual(IFunctionElement e) {
        return !this.equals(e);
    }

    @Override
    public boolean and(IFunctionElement e) {
        throw new UnsupportedOperationException(IllegalOperationMessage("and"));
    }

    @Override
    public boolean or(IFunctionElement e) {
        throw new UnsupportedOperationException(IllegalOperationMessage("or"));
    }

    @Override
    public IFunctionElement range(IFunctionElement e) {
        throw new UnsupportedOperationException(IllegalOperationMessage("range"));
    }

    @Override
    public IFunctionElement addNum(double num) {
        throw new UnsupportedOperationException(IllegalOperationMessage("addNum"));
    }

    @Override
    public IFunctionElement subNum(double num) {
        throw new UnsupportedOperationException(IllegalOperationMessage("subNum"));
    }

    @Override
    public IFunctionElement multNum(double num) {
        throw new UnsupportedOperationException(IllegalOperationMessage("multNum"));
    }

    @Override
    public IFunctionElement divNum(double num) {
        throw new UnsupportedOperationException(IllegalOperationMessage("divNum"));
    }

    @Override
    public boolean numLessThan(double num) {
        throw new UnsupportedOperationException(IllegalOperationMessage("numLessThan"));
    }

    @Override
    public boolean numGreaterThan(double num) {
        throw new UnsupportedOperationException(IllegalOperationMessage("numGreaterThan"));
    }

    @Override
    public boolean andNum(double num) {
        throw new UnsupportedOperationException(IllegalOperationMessage("andNum"));
    }

    @Override
    public boolean orNum(double num) {
        throw new UnsupportedOperationException(IllegalOperationMessage("orNum"));
    }

    @Override
    public boolean equalsRef(IRef ref) {
        throw new UnsupportedOperationException(IllegalOperationMessage("equalsRef"));
    }
    
}
