package server;

import Server.model.Publisher;
import Server.model.Spreadsheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublisherTest {
    private Publisher publisher;
    private List<Spreadsheet> spreadsheets;

    @BeforeEach
    public void setUp() {
        spreadsheets = new ArrayList<>();
        publisher = new Publisher("Test Publisher", spreadsheets);
    }

    @Test
    public void testGetName() {
        assertEquals("Test Publisher", publisher.getName());
    }

    @Test
    public void testAddSpreadsheet() {
        Spreadsheet spreadsheet = new Spreadsheet(publisher, "Test Spreadsheet");
        publisher.addSpreadsheet(spreadsheet);
        assertTrue(publisher.getSpreadsheets().contains(spreadsheet));
    }

    @Test
    public void testRemoveSpreadsheet() {
        Spreadsheet spreadsheet = new Spreadsheet(publisher,"Test Spreadsheet");
        publisher.addSpreadsheet(spreadsheet);
        publisher.removeSpreadsheet(spreadsheet);
        assertTrue(!publisher.getSpreadsheets().contains(spreadsheet));
    }

    @Test
    public void testGetSpreadsheets() {
        spreadsheets = new ArrayList<>();
        spreadsheets.add(new Spreadsheet(publisher, "Spreadsheet 1"));
        spreadsheets.add(new Spreadsheet(publisher, "Spreadsheet 2"));
        spreadsheets.add(new Spreadsheet(publisher, "Spreadsheet 3"));
        List<Spreadsheet> actualSpreadsheets = publisher.getSpreadsheets();
        assertEquals(spreadsheets.size(), actualSpreadsheets.size());

        for (int i = 0; i < spreadsheets.size(); i++) {
            assertEquals(spreadsheets.get(i).getSheetName(),
                    actualSpreadsheets.get(i).getSheetName() );
        }
    }
}