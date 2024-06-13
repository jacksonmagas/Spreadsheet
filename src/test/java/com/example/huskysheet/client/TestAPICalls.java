package com.example.huskysheet.client;

import com.example.huskysheet.client.Model.ISpreadsheet;
import com.example.huskysheet.client.Utils.Coordinate;
import java.net.URISyntaxException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestAPICalls {
    private String testSheet;
    private static final String PROF_SERVER_URL = "https://husksheets.fly.dev";
    private static final String USERNAME = "team4";
    private static final String PASSWORD = "IgUraDn4(kS>_-7>";
    private static SpreadsheetManager spreadsheetManager;

    @BeforeAll
    public static void init() throws URISyntaxException {
        spreadsheetManager = new SpreadsheetManager(PROF_SERVER_URL, USERNAME, PASSWORD);
    }

    @AfterEach
    public void cleanup() {
        try {
            spreadsheetManager.deleteSpreadsheet();
        } catch (Exception ignored) {

        }
    }

    @Test
    public void testRegister() {
        Assertions.assertDoesNotThrow(() -> {spreadsheetManager.register();});
    }

    @Test
    public void testGetPublishers() throws APICallException {
        spreadsheetManager.register();
        Assertions.assertTrue(spreadsheetManager.getPublishers().contains(USERNAME));
    }

    @Test
    public void testGetSheets() throws APICallException {
        testSheet = "test-sheet-" + System.currentTimeMillis();
        spreadsheetManager.register();
        spreadsheetManager.createSpreadsheet(testSheet);
        Assertions.assertTrue(spreadsheetManager.getAvailableSheets(USERNAME).contains(testSheet));
    }


    @Test
    public void testDeleteSheet() throws APICallException {
        testSheet = "test-sheet-" + System.currentTimeMillis();
        spreadsheetManager.register();
        spreadsheetManager.createSpreadsheet(testSheet);
        spreadsheetManager.deleteSpreadsheet();
        Assertions.assertFalse(spreadsheetManager.getAvailableSheets(USERNAME).contains(testSheet));
    }

    @Test
    public void testGetSheet() throws APICallException {
        testSheet = "test-sheet-" + System.currentTimeMillis();
        spreadsheetManager.register();
        spreadsheetManager.createSpreadsheet(testSheet);
        ISpreadsheet sheet = spreadsheetManager.getSpreadsheet(USERNAME, testSheet);
        Assertions.assertEquals(0, sheet.numColumns());
        Assertions.assertEquals(0, sheet.numRows());
    }

    @Test
    public void testUpdateSheetAndGetUpdates() throws APICallException, URISyntaxException {
        testSheet = "test-sheet-" + System.currentTimeMillis();
        spreadsheetManager.register();
        spreadsheetManager.createSpreadsheet(testSheet);
        ISpreadsheet sheet = spreadsheetManager.getSpreadsheet(USERNAME, testSheet);
        sheet.getCell(new Coordinate(5, 5)).updateCell("=5+5");
        sheet.getCell(new Coordinate(10, 20)).updateCell("bar");
        var manager2 = new SpreadsheetManager(PROF_SERVER_URL, USERNAME, PASSWORD);
        ISpreadsheet sheetCopy = manager2.getSpreadsheet(USERNAME, testSheet);
        Assertions.assertEquals(sheet.getCell(new Coordinate(5, 5)).getPlaintext(),
            sheetCopy.getCell(new Coordinate(5, 5)).getPlaintext());
        Assertions.assertEquals(sheet.getCell(new Coordinate(10, 20)).getPlaintext(),
            sheetCopy.getCell(new Coordinate(10, 20)).getPlaintext());
        sheet.getCell(new Coordinate(5, 5)).updateCell("=\"Hello\"");
        manager2.getUpdates();
        Assertions.assertEquals(sheet.getCell(new Coordinate(5, 5)).getPlaintext(),
            sheetCopy.getCell(new Coordinate(5, 5)).getPlaintext());
    }
}
