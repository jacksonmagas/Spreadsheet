public class Ref implements IRef{

    private int column;
    private int row;

    // TODO: Written by GPT - TEST!!!!!!
    private String columnToString() {
        StringBuilder result = new StringBuilder();
        
        while (column > 0) {
            int remainder = column % 26;
            char digit = (char) ('A' + remainder);
            result.append(digit);
            column /= 26;
        }

        return result.reverse().toString();
    }

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
