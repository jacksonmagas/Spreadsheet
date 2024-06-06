package Model;

import Model.Utils.Coordinate;

public class CellWrapper {
    private String value; // Change the type to String
    private Coordinate coordinate;
    private String plaintext;
    private final ICell cell;

    public CellWrapper(ICell cell) {
        this.cell = cell;
        this.value = cell.getData();
        this.coordinate = cell.getCoordinate();
        this.plaintext = cell.getPlaintext();
    }

    public String getValue() {
        return cell.getData();
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

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }
}
