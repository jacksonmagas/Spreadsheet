package Model.Utils;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
       return obj instanceof Coordinate
           && ((Coordinate) obj).row == row
           && ((Coordinate) obj).column == column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
