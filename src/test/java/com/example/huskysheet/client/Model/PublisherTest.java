package com.example.huskysheet.client.Model;

import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Spreadsheet;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublisherTest {

  private Publisher publisher;
  private List<Spreadsheet> spreadsheets;

  @BeforeEach
  public void setUp() {
    spreadsheets = new ArrayList<>();
    publisher = new Publisher("admin", spreadsheets);
  }

  @Test
  public void testGetName() {
    assertEquals("admin", publisher.getName());
  }

  @Test
  public void testAddSpreadsheet() {
    Spreadsheet spreadsheet = new Spreadsheet(publisher, "Sheet1");
    publisher.addSpreadsheet(spreadsheet);
    assertTrue(publisher.getSpreadsheets().contains(spreadsheet));
    assertEquals(1, publisher.getSpreadsheets().size());
  }

  @Test
  public void testRemoveSpreadsheet() {
    Spreadsheet spreadsheet = new Spreadsheet(publisher, "Sheet1");
    publisher.addSpreadsheet(spreadsheet);
    publisher.removeSpreadsheet(spreadsheet);
    assertFalse(publisher.getSpreadsheets().contains(spreadsheet));
    assertEquals(0, publisher.getSpreadsheets().size());
  }

  @Test
  public void testRemoveNonExistentSpreadsheet() {
    Spreadsheet spreadsheet1 = new Spreadsheet(publisher, "Sheet1");
    Spreadsheet spreadsheet2 = new Spreadsheet(publisher, "Sheet2");
    publisher.addSpreadsheet(spreadsheet1);
    publisher.removeSpreadsheet(spreadsheet2);
    assertTrue(publisher.getSpreadsheets().contains(spreadsheet1));
    assertFalse(publisher.getSpreadsheets().contains(spreadsheet2));
    assertEquals(1, publisher.getSpreadsheets().size());
  }

  @Test
  public void testGetSpreadsheets() {
    Spreadsheet spreadsheet1 = new Spreadsheet(publisher, "Sheet1");
    Spreadsheet spreadsheet2 = new Spreadsheet(publisher, "Sheet2");
    publisher.addSpreadsheet(spreadsheet1);
    publisher.addSpreadsheet(spreadsheet2);
    List<Spreadsheet> spreadsheets = publisher.getSpreadsheets();
    assertEquals(2, spreadsheets.size());
    assertTrue(spreadsheets.contains(spreadsheet1));
    assertTrue(spreadsheets.contains(spreadsheet2));
  }

  @Test
  public void testSetSpreadsheets() {
    Spreadsheet spreadsheet1 = new Spreadsheet(publisher, "Sheet1");
    Spreadsheet spreadsheet2 = new Spreadsheet(publisher, "Sheet2");
    List<Spreadsheet> newSpreadsheets = new ArrayList<>();
    newSpreadsheets.add(spreadsheet1);
    newSpreadsheets.add(spreadsheet2);
    publisher.setSpreadsheets(newSpreadsheets);
    List<Spreadsheet> spreadsheets = publisher.getSpreadsheets();
    assertEquals(2, spreadsheets.size());
    assertTrue(spreadsheets.contains(spreadsheet1));
    assertTrue(spreadsheets.contains(spreadsheet2));
  }

  @Test
  public void testSetEmptySpreadsheets() {
    List<Spreadsheet> newSpreadsheets = new ArrayList<>();
    publisher.setSpreadsheets(newSpreadsheets);
    List<Spreadsheet> spreadsheets = publisher.getSpreadsheets();
    assertEquals(0, spreadsheets.size());
  }

  @Test
  public void testSetNullSpreadsheets() {
    publisher.setSpreadsheets(null);
    assertNull(publisher.getSpreadsheets());
  }

  @Test
  public void testAddNullSpreadsheet() {
    publisher.addSpreadsheet(null);
    assertTrue(publisher.getSpreadsheets().contains(null));
    assertEquals(1, publisher.getSpreadsheets().size());
  }

  @Test
  public void testRemoveNullSpreadsheet() {
    publisher.addSpreadsheet(null);
    publisher.removeSpreadsheet(null);
    assertFalse(publisher.getSpreadsheets().contains(null));
    assertEquals(0, publisher.getSpreadsheets().size());
  }
}
