package Client.Model;

import Client.Model.Utils.Coordinate;

import java.util.List;
import javafx.util.Pair;

public interface ISpreadsheet {
    // Return the cell at the given coordinates, creating a new cell object if the cell is empty.
    ICell getCell(Coordinate coordinate);

    int numRows();

    int numColumns();


    List<ICell> getRow(int rowNum);

    List<ICell> getColumn(int colNum);

    // Update the cell at each coordinate to the given string
    void updateSheet(List<Pair<Coordinate, String>> update);

    // register a new listener for changes to this spreadsheet
    void registerListener(ISpreadsheetListener listener);

    // unregister a given listener if it is registered
    void unregisterListener(ISpreadsheetListener listener);

    // notify listeners of a change to the spreadsheet
    void notifyListeners(Coordinate coordinate, String update);

    /**
     * Set a format option for a row or column
     * TODO
     */

    /*
    void setRowColor(int row);

    void setColumnColor(int col);

    void setRowFont(int row);

    void setColumnFont(int col);
     */
}
