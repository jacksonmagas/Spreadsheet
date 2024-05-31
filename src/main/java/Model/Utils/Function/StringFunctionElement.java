package Model.Utils.Function;

public class StringFunctionElement extends AbstractFunctionElement {

    private String value;

    public StringFunctionElement(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(IFunctionElement e) {
        return e.equalsVal(value);
    }

    @Override
    public boolean notEqual(IFunctionElement e) {
        return !e.equalsVal(value);
    }

    @Override
    public boolean equalsVal(Object o) {
        return this.value.equals(o);
    }
}
