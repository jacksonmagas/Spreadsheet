package com.example.huskysheet.client.Model;

import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Spreadsheet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpreadsheetTest {

  private Spreadsheet spreadsheet;
  private Publisher publisher;

  @BeforeEach
  public void setUp() {
    publisher = new Publisher("admin", null);
    spreadsheet = new Spreadsheet(publisher, "Sheet1");
  }

  @Test
  public void testSpreadsheetInitialization() {
    assertNotNull(spreadsheet);
    assertEquals("Sheet1", spreadsheet.getSheetName());
    assertEquals(0, spreadsheet.getLastPublishedUpdateId());
    assertEquals(0, spreadsheet.getLastSubscriptionUpdateId());
  }

  @Test
  public void testAddPublishedUpdate() {
    spreadsheet.addPublishedUpdate("Update1");
    assertEquals(1, spreadsheet.getLastPublishedUpdateId());
    List<String> updates = spreadsheet.getUpdatesAfterId("0");
    assertEquals(1, updates.size());
    assertEquals("1,Update1", updates.get(0));
  }

  @Test
  public void testAddSubscriptionUpdate() {
    spreadsheet.addSubscriptionUpdate("SubscriptionUpdate1");
    assertEquals(1, spreadsheet.getLastSubscriptionUpdateId());
    List<String> updateRequests = spreadsheet.getUpdateRequestsAfterId("0");
    assertEquals(1, updateRequests.size());
    assertEquals("1,SubscriptionUpdate1", updateRequests.get(0));
  }

  @Test
  public void testGetUpdatesAfterId() {
    spreadsheet.addPublishedUpdate("Update1");
    spreadsheet.addPublishedUpdate("Update2");
    List<String> updates = spreadsheet.getUpdatesAfterId("0");
    assertEquals(2, updates.size());
    assertEquals("1,Update1", updates.get(0));
    assertEquals("2,Update2", updates.get(1));

    updates = spreadsheet.getUpdatesAfterId("1");
    assertEquals(1, updates.size());
    assertEquals("2,Update2", updates.get(0));
  }

  @Test
  public void testGetUpdateRequestsAfterId() {
    spreadsheet.addSubscriptionUpdate("SubscriptionUpdate1");
    spreadsheet.addSubscriptionUpdate("SubscriptionUpdate2");
    List<String> updateRequests = spreadsheet.getUpdateRequestsAfterId("0");
    assertEquals(2, updateRequests.size());
    assertEquals("1,SubscriptionUpdate1", updateRequests.get(0));
    assertEquals("2,SubscriptionUpdate2", updateRequests.get(1));

    updateRequests = spreadsheet.getUpdateRequestsAfterId("1");
    assertEquals(1, updateRequests.size());
    assertEquals("2,SubscriptionUpdate2", updateRequests.get(0));
  }

  @Test
  public void testSetPayload() {
    spreadsheet.setPayload("PayloadUpdate1");
    assertEquals(1, spreadsheet.getLastPublishedUpdateId());
    List<String> updates = spreadsheet.getUpdatesAfterId("0");
    assertEquals(1, updates.size());
    assertEquals("1,PayloadUpdate1", updates.get(0));
  }

  @Test
  public void testAddNullUpdate() {
    assertThrows(NullPointerException.class, () -> {
      spreadsheet.addPublishedUpdate(null);
    });
  }

  @Test
  public void testAddNullSubscriptionUpdate() {
    assertThrows(NullPointerException.class, () -> {
      spreadsheet.addSubscriptionUpdate(null);
    });
  }

  @Test
  public void testInvalidIdInGetUpdatesAfterId() {
    spreadsheet.addPublishedUpdate("Update1");
    assertThrows(NumberFormatException.class, () -> {
      spreadsheet.getUpdatesAfterId("invalid");
    });
  }

  @Test
  public void testInvalidIdInGetUpdateRequestsAfterId() {
    spreadsheet.addSubscriptionUpdate("SubscriptionUpdate1");
    assertThrows(NumberFormatException.class, () -> {
      spreadsheet.getUpdateRequestsAfterId("invalid");
    });
  }
}
