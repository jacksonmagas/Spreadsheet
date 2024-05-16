public interface ICell<T> {
    // returns the location of this cell
    Coordinate getCoordinate();

    // returns the data contained in this cell
    T getData();

    // returns true if the cell is empty
    boolean isEmpty();
}