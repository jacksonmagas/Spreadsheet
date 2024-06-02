package Model.Utils.Function;

import Model.Utils.Coordinate;

/*
 * A function element can be a string, integer, or cell referance.
 */
public interface IFunctionElement {
    // basic ops
    IFunctionElement add(IFunctionElement e);
    IFunctionElement subtract(IFunctionElement e);
    IFunctionElement multiply(IFunctionElement e);
    IFunctionElement divide(IFunctionElement e);
    boolean lessThan(IFunctionElement e);
    boolean greaterThan(IFunctionElement e);
    boolean equals(IFunctionElement e);
    boolean notEqual(IFunctionElement e);
    boolean and(IFunctionElement e);
    boolean or(IFunctionElement e);

    // Returns the range of cells 
    IFunctionElement range(IFunctionElement e);

    // eq op
    boolean equalsVal(Object o);

    // num op
    IFunctionElement addNum(double num);
    IFunctionElement subNum(double num);
    IFunctionElement multNum(double num);
    IFunctionElement divNum(double num);
    boolean numLessThan(double num);
    boolean numGreaterThan(double num);
    boolean andNum(double num);
    boolean orNum(double num);

    // ref op
    boolean equalsRef(Coordinate ref);
}
