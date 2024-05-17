public interface IRef {
    // get the cell this refence points to
    ICell getCell();

    // get the row number of this reference
    int getRow();

    // get the column number of this reference
    int getColumn();

    // get the string reference to the cell in the cell language, for example the upper left cell would be $A1
    String toString();
}
