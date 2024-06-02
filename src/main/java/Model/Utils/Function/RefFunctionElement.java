package Model.Utils.Function;

import Model.Utils.Coordinate;

/*
 * Represents a cell referance element in an IFunction.
 */
public class RefFunctionElement extends AbstractFunctionElement {

    private Coordinate value;

    public RefFunctionElement(Coordinate value) {
        this.value = value;
    }

    // TODO: implement range method
    @Override
    public IFunctionElement range(IFunctionElement e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'range'");
    }

    @Override
    public boolean equalsVal(Object o) {
        return this.value.equals(o);
    }

    @Override
    public boolean equals(IFunctionElement e) {
        boolean returnVal;
        try {
            returnVal = e.equalsRef(this.value);
        } catch (UnsupportedOperationException exception) {
            returnVal = false;
        }
        return returnVal;
    }
    
}
