package Model;

import Model.Utils.Coordinate;

public class CellFormat {
    private String value; // Change the type to String
    private Coordinate coordinate;

    public CellFormat(String value, Coordinate coordinate) {
        this.value = value;
        this.coordinate = coordinate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
