package Server;

import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Spreadsheet;
import com.example.huskysheet.api.Server.GetUpdatesRequest;
import com.example.huskysheet.model.UpdatePayload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class APIGetUpdatesForSubscriptionTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getUpdatesForSubscription_validRequest() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    sheet.addUpdate("2,Update1");
    sheet.addUpdate("3,Update2");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    GetUpdatesRequest request = new GetUpdatesRequest("testPublisher", "Sheet1", "1");

    // Act
    ResponseEntity<?> response = controller.getUpdatesForSubscription(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    UpdatePayload payload = (UpdatePayload) response.getBody();
    assertEquals(2, payload.getUpdates().size());
    assertEquals("2,Update1", payload.getUpdates().get(0));
    assertEquals("3,Update2", payload.getUpdates().get(1));
  }

  @Test
  void getUpdatesForSubscription_sheetNotFound() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    GetUpdatesRequest request = new GetUpdatesRequest("testPublisher", "NonExistentSheet", "1");

    // Act
    ResponseEntity<?> response = controller.getUpdatesForSubscription(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Sheet not found", response.getBody());
  }

  @Test
  void getUpdatesForSubscription_publisherNotFound() {
    // Arrange
    when(publishers.getPublisherByUsername("nonExistentPublisher")).thenReturn(null);

    GetUpdatesRequest request = new GetUpdatesRequest("nonExistentPublisher", "Sheet1", "1");

    // Act
    ResponseEntity<?> response = controller.getUpdatesForSubscription(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Publisher not found", response.getBody());
  }

  @Test
  void getUpdatesForSubscription_invalidId() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    sheet.addUpdate("1,InitialUpdate");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    GetUpdatesRequest request = new GetUpdatesRequest("testPublisher", "Sheet1", "nonIntegerId");

    // Act
    ResponseEntity<?> response = controller.getUpdatesForSubscription(request);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }
}

