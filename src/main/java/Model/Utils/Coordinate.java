package Model.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coordinate {
    private int column;
    private int row;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Alternative coordinate constructor from string representation $AA12
     * @param coordinate the string representation of the coordinate
     */
    public Coordinate(String coordinate) throws IllegalArgumentException {
        Coordinate temp = Conversions.stringToCoordinate(coordinate);
        this.row = temp.getRow();
        this.column = temp.getColumn();
    }

    /**
     * Get a list of all coordinates from this to the target coordinate.
     * @param target the end coordinate for the range inclusive
     * @return A list of coordinates in the range
     * @throws IllegalArgumentException If the target is less than this
     * Jackson Magas
     */
    public List<Coordinate> getRange(Coordinate target) throws IllegalArgumentException {
        if (this.compareTo(target) != -1) {
            throw new IllegalArgumentException("Target less than this coordinate");
        }

        List<Coordinate> result = new ArrayList<>();
        for (int i = this.row; i <= target.row; i++) {
            for (int j = this.column; j <= target.column; j++) {
                result.add(new Coordinate(i, j));
            }
        }

        return result;
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

    /**
     * A coordinate is larger than another coordinate if it is larger in both coordinates
     * @param o the coordinate to compare with
     * @return 1 if this is larger, -1 if this is smaller, and 0 if it is neither
     * Jackson Magas
     */
    public int compareTo(Coordinate o) {
        if (this.row >= o.row && this.column >= o.column) {
            return 1;
        } else if (this.row <= o.row && this.column <= o.column) {
            return -1;
        } else {
            return 0;
        }
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
