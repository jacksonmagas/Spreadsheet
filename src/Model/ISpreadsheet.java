package Model;

import Model.Utils.IRef;
import Model.Utils.ITerm;
import java.util.UUID;

import java.util.List;
import javafx.util.Pair;

public interface ISpreadsheet {
    // Return the cell at the given coordinates, creating a new cell object if the cell is empty.
    ICell getCell(int row, int column);

    // Return the ID of this spreadsheet
    UUID getId();

    // Update the cell pointed to by each reference 
    
    void updateSheet(List<Pair<IRef, ITerm>> update);
}
