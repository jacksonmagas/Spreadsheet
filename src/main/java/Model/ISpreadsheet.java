package Model;

import Model.Utils.Coordinate;
import Model.Utils.ITerm;
import java.util.UUID;

import java.util.List;
import javafx.util.Pair;

public interface ISpreadsheet {
    // Return the cell at the given coordinates, creating a new cell object if the cell is empty.
    ICell getCell(Coordinate coordinate);

    // Return the ID of this spreadsheet
    UUID getId();

    // Update the cell pointed to by each reference 
    
    void updateSheet(List<Pair<Coordinate, ITerm>> update);


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
