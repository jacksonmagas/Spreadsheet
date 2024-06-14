package com.example.huskysheet.client.Model;

import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Spreadsheet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Test that the Spreadsheet class works
 * @author Katie Winkleblack
 */

public class TestSpreadsheet {

  private Publisher testPublisher;
  private Spreadsheet testSpreadsheet;

  @BeforeEach
  public void setUp() {
    testPublisher = new Publisher("TestPublisher", new ArrayList<>());
    testSpreadsheet = new Spreadsheet(testPublisher, "TestSpreadsheet");
  }

  @Test
  public void testConstructor() {
    assertEquals("TestSpreadsheet", testSpreadsheet.getSheetName());
  }

  @Test
  public void testConstructvorWithNullPublisher() {
    assertThrows(IllegalArgumentException.class, () -> new Spreadsheet(null, "TestSpreadsheet"));
  }

  @Test
  public void testNullPubConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new Spreadsheet(null,
            "TestSpreadsheet"));
  }

  @Test
  public void testAddPublishedUpdate() {
    testSpreadsheet.addPublishedUpdate("Updated TestSpreadsheet Once");
    assertEquals(1, testSpreadsheet.getUpdatesAfterId("0").size());
  }

  @Test
  public void testAddSubscriptionUpdate() {
    testSpreadsheet.addSubscriptionUpdate("Update Request");
    assertEquals(1, testSpreadsheet.getUpdateRequestsAfterId("0").size());
  }

  @Test
  public void testGetUpdatesAfterId() {
    testSpreadsheet.addPublishedUpdate("update 1");
    testSpreadsheet.addPublishedUpdate("update 2");
    assertEquals(2, testSpreadsheet.getUpdatesAfterId("0").size());
    assertEquals(1, testSpreadsheet.getUpdatesAfterId("1").size());
  }

  @Test void testGetUpdateRequestsAfterId() {
    testSpreadsheet.addSubscriptionUpdate("request 1");
    testSpreadsheet.addSubscriptionUpdate("request 2");
    assertEquals(2, testSpreadsheet.getUpdateRequestsAfterId("0").size());
    assertEquals(1, testSpreadsheet.getUpdateRequestsAfterId("1").size());
  }
  @Test
  public void testSetAndGetPayload() {
    testSpreadsheet.setPayload("Payload");
    assertEquals(1, testSpreadsheet.getUpdatesAfterId("0").size());
    assertTrue(testSpreadsheet.getUpdatesAfterId("0").contains("1,Payload"));
  }
}

