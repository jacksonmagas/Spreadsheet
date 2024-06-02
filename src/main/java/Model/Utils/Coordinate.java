package Model.Utils;

public class Coordinate {
    private int column;
    private int row;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String toString() {
        return "$" + Conversions.columnToString(column) + row;
    }
}
