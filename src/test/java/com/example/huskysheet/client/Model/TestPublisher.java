
package com.example.huskysheet.client.Model;


import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Spreadsheet;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Test that the Publisher class works accordinly
 * @author Katie Winkleblack
 */

public class TestPublisher {

  @Test
  public void testPublisherFunctions() {
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    spreadsheets.add(new Spreadsheet(new Publisher("testPublisher",
            spreadsheets), "Sheet1"));
    spreadsheets.add(new Spreadsheet(new Publisher("testPublisher",
            spreadsheets), "Sheet2"));

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    assertEquals("testPublisher", publisher.getName());
    assertEquals(spreadsheets, publisher.getSpreadsheets());

    // tests addSpreadsheet and getSpreadsheets
   Spreadsheet newS =  new Spreadsheet(new Publisher("testPublisher", spreadsheets),
            "sheet3");
    publisher.addSpreadsheet(newS);
    assertTrue(publisher.getSpreadsheets().contains(newS));

    // test removeSpreadsheet
    publisher.removeSpreadsheet(newS);
    assertFalse(publisher.getSpreadsheets().contains(newS));
  }


}

