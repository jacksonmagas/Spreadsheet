import java.util.UUID;

public interface ICell<T> {
    // TODO: Do cells need ids?
    UUID getId();

    // returns the location of this cell
    Coordinate getCoordinate();

    // returns the data contained in this cell
    T getData();

    // returns true if the cell is empty
    boolean isEmpty();
}