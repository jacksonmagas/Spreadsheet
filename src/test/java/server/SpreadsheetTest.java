package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import Server.model.Publisher;
import Server.model.Spreadsheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpreadsheetTest {
    
    private Publisher publisher;
    private Spreadsheet spreadsheet;

    @BeforeEach
    public void setUp() {
        publisher = new Publisher("TestPublisher", new ArrayList<>());
        spreadsheet = new Spreadsheet(publisher, "TestSpreadsheet");
    }

    @Test
    public void testConstructorWithNullPublisher() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Spreadsheet(null, "TestSpreadsheet");
        });
        assertEquals("Spreadsheet must have a Publisher", exception.getMessage());
    }

    @Test
    public void testGetSheetName() {
        assertEquals("TestSpreadsheet", spreadsheet.getSheetName());
    }

    @Test
    public void testAddUpdateAndGetUpdatesAfterId() {
        spreadsheet.addUpdate("1, Update 1");
        spreadsheet.addUpdate("2, Update 2");
        spreadsheet.addUpdate("3, Update 3");

        List<String> updates = spreadsheet.getUpdatesAfterId("1");
        assertEquals(2, updates.size());
        assertTrue(updates.contains("2, Update 2"));
        assertTrue(updates.contains("3, Update 3"));

        updates = spreadsheet.getUpdatesAfterId("2");
        assertEquals(1, updates.size());
        assertTrue(updates.contains("3, Update 3"));
    }

    @Test
    public void testAddUpdateRequestAndGetUpdateRequestsAfterId() {
        spreadsheet.addUpdateRequest("1, Request 1");
        spreadsheet.addUpdateRequest("2, Request 2");
        spreadsheet.addUpdateRequest("3, Request 3");

        List<String> requests = spreadsheet.getUpdateRequestsAfterId("1");
        assertEquals(2, requests.size());
        assertTrue(requests.contains("2, Request 2"));
        assertTrue(requests.contains("3, Request 3"));

        requests = spreadsheet.getUpdateRequestsAfterId("2");
        assertEquals(1, requests.size());
        assertTrue(requests.contains("3, Request 3"));
    }

    @Test
    public void testSetAndGetPayload() {
        spreadsheet.setPayload("Test Payload");
        assertEquals("Test Payload", spreadsheet.getPayload());
    }

    @Test
    public void testGetSheet() {
        assertSame(spreadsheet, spreadsheet.getSheet());
    }
}