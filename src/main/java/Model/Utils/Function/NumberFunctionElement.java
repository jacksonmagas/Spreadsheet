package Model.Utils.Function;

public class NumberFunctionElement extends AbstractFunctionElement {

    private double value;

    public NumberFunctionElement(double value) {
        this.value = value;
    }

    @Override
    public IFunctionElement add(IFunctionElement e) {
        return e.addNum(value);
    }

    @Override
    public IFunctionElement subtract(IFunctionElement e) {
        return e.subNum(value);
    }

    @Override
    public IFunctionElement multiply(IFunctionElement e) {
        return e.multNum(value);
    }

    @Override
    public IFunctionElement divide(IFunctionElement e) {
        return e.divNum(value);
    }

    @Override
    public boolean lessThan(IFunctionElement e) {
        return e.numLessThan(value);
    }

    @Override
    public boolean greaterThan(IFunctionElement e) {
        return e.numGreaterThan(value);
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
    public boolean and(IFunctionElement e) {
        return e.andNum(value);
    }

    @Override
    public boolean or(IFunctionElement e) {
        return e.orNum(value);
    }

    @Override
    public boolean equalsVal(Object o) {
        return o.equals(this.value);
    }

    @Override
    public IFunctionElement addNum(double num) {
        return new NumberFunctionElement(this.value + num);
    }

    @Override
    public IFunctionElement subNum(double num) {
        return new NumberFunctionElement(num - this.value);
    }

    @Override
    public IFunctionElement multNum(double num) {
        return new NumberFunctionElement(num * this.value);
    }

    @Override
    public IFunctionElement divNum(double num) {
        return new NumberFunctionElement(num / this.value);
    }

    @Override
    public boolean numLessThan(double num) {
        return num < this.value;
    }

    @Override
    public boolean numGreaterThan(double num) {
        return num > this.value;
    }

    @Override
    public boolean andNum(double num) {
        return num != 0 && this.value != 0;
    }

    @Override
    public boolean orNum(double num) {
        return num != 0 || this.value != 0;
    }
}
