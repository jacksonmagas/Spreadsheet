package Model.Utils;

import Model.ICell;
import java.util.List;

public class ReferenceExpression extends Expression {
    ICell reference;

    public ReferenceExpression(ICell cell) {
        reference = cell;
    }

    @Override
    public List<Coordinate> dependencies() {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void recalculate() {

    }
}
