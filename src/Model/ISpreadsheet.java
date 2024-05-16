import java.util.UUID;

public interface ISpreadsheet {
    // Return the cell at the given coordinates
    ICell<?> getCell(int x, int y);

    // Return the ID of this spreadsheet
    UUID getId();

    // Sends request to server to get data for the sheet with the given UUID
    ISpreadsheet loadSpreadsheet(UUID id);
}
