package Client;

import Model.ISpreadsheet;
import Model.ISpreadsheetListener;
import Model.Utils.Coordinate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * Class to produce spreadsheets from the remote database and ensure the remote is properly updated.
 * TODO implement
 * TODO add caching where possible
 */
public class SpreadsheetManager implements ISpreadsheetListener {
    private final String authHeader;
    private final String serverUrl;
    private int currentID;
    private ISpreadsheet currentSpreadsheet;
    private String currentSheetName;

    public SpreadsheetManager(String serverUrl, String userName, String password) {
        this.serverUrl = serverUrl;
        authHeader = "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
    }

    /**
     * Register with the server using the username and password
     * @return true if the request is successful
     */
    public boolean register() {
        return false;//todo
    }

    /**
     * Get a list of available spreadsheets from the server.
     * @return An optional containing the list of sheets if the request was successful, otherwise an
     * empty optional
     */
    public Optional<List<String>> getAvailableSheets() {
        return Optional.empty();//todo
    }

    /**
     * Gets a specific spreadsheet from the server
     * @param sheetName the sheet to get
     * @return An optional containing the spreadsheet if the request was successful, and an
     * empty optional otherwise
     */
    public Optional<ISpreadsheet> getSpreadsheet(String sheetName) {
        // TODO
        // make api call to getUpdates
        // handle error returns
        // parse valid return into spreadsheet
        Optional<ISpreadsheet> sheet = Optional.empty();
        sheet.ifPresent(s -> {
            currentSpreadsheet.unregisterListener(this);
            currentSpreadsheet = s;
            currentSpreadsheet.registerListener(this);
            currentSheetName = sheetName;});
        return sheet;
    }

    /**
     * Get all updates for the current sheet from the server and apply them to the current spreadsheet.
     * @return true if the request was successful,
     * false if the request was unsuccessful or the current spreadsheet isn't set.
     */
    public boolean getUpdates() {
        return false;//todo
    }

    /**
     * Publish a new spreadsheet with the given name on the server
     * @param sheetName the name of the new sheet to create
     * @return true of the request is successful, false otherwise
     */
    public boolean createSpreadsheet(String sheetName) {
        return false;//TODO
    }

    /**
     * Delete the spreadsheet with the given name from the server
     * @param sheetName the name of the sheet to delete
     * @return true if the request is successful, false otherwise
     */
    public boolean deleteSpreadsheet(String sheetName) {
        return false;//TODO
    }

    /**
     * Update the server based on the new spreadsheet update
     *
     * @param update the string entered by the user in the cell
     */
    @Override
    public void handleUpdate(Coordinate coordinate, String update) {

    }
}
