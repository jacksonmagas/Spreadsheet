package ModelTestingServer;

import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Spreadsheet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
  public void testUpdateAdd() {
    testSpreadsheet.addUpdate("1, Updated TestSpreadsheet Once");
    assertEquals(1, testSpreadsheet.getUpdatesAfterId("0").size());
  }

  @Test
  public void testAddUpdateRequest() {
    testSpreadsheet.addUpdateRequest("1, Update Request");
    assertEquals(1, testSpreadsheet.getUpdateRequestsAfterId("0").size());
  }

  @Test
  public void testSetAndGetPayload() {
    testSpreadsheet.setPayload("Payload");
    assertEquals("Payload", testSpreadsheet.getPayload());
  }
}
