public class Ref implements IRef{

    private int column;
    private int row;

    @Override
    public ICell getCell() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCell'");
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "$" + columnToString() + row;
    }
}
