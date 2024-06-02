package Model.Utils;

public class EmptyTerm implements ITerm {
    @Override
    public String getResult() {
        return "";
    }

    @Override
    public void recalculate() {

    }
}
