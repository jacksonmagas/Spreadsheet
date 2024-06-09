package Server;

import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Spreadsheet;
import com.example.huskysheet.model.UpdatePayload;
import com.example.huskysheet.api.Server.GetUpdatesRequest;

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

class APIGetUpdatesForPublishedTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getUpdatesForPublished_validRequest() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    sheet.addUpdateRequest("2,Request1");
    sheet.addUpdateRequest("3,Request2");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    GetUpdatesRequest request = new GetUpdatesRequest("testPublisher", "Sheet1", "1");

    // Act
    ResponseEntity<?> response = controller.getUpdatesForPublished(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    UpdatePayload payload = (UpdatePayload) response.getBody();
    assertEquals(2, payload.getUpdates().size());
    assertEquals("2,Request1", payload.getUpdates().get(0));
    assertEquals("3,Request2", payload.getUpdates().get(1));
  }

  @Test
  void getUpdatesForPublished_sheetNotFound() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    GetUpdatesRequest request = new GetUpdatesRequest("testPublisher", "NonExistentSheet", "1");

    // Act
    ResponseEntity<?> response = controller.getUpdatesForPublished(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Sheet not found", response.getBody());
  }

  @Test
  void getUpdatesForPublished_publisherNotFound() {
    // Arrange
    when(publishers.getPublisherByUsername("nonExistentPublisher")).thenReturn(null);

    GetUpdatesRequest request = new GetUpdatesRequest("nonExistentPublisher", "Sheet1", "1");

    // Act
    ResponseEntity<?> response = controller.getUpdatesForPublished(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Publisher not found", response.getBody());
  }

  @Test
  void getUpdatesForPublished_noUpdatesFound() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    GetUpdatesRequest request = new GetUpdatesRequest("testPublisher", "Sheet1", "1");

    // Act
    ResponseEntity<?> response = controller.getUpdatesForPublished(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    UpdatePayload payload = (UpdatePayload) response.getBody();
    assertEquals(0, payload.getUpdates().size());
  }
}

