package Model.Utils;

import java.util.List;

public class EmptyTerm implements ITerm {
    @Override
    public String getResult() {
        return "";
    }

    @Override
    public ResultType resultType() {
        return ResultType.empty;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void recalculate() {

    }

    @Override
    public List<Coordinate> dependencies() {
        return List.of();
    }
}
