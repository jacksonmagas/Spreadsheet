package server;

import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Spreadsheet;
import com.example.huskysheet.api.Server.UpdateRequest;

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

class UpdatePublishedControllerTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void updatePublished_success() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    UpdateRequest request = new UpdateRequest("testPublisher", "Sheet1", "New payload");

    // Act
    ResponseEntity<String> response = controller.updatePublished(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Sheet updated successfully", response.getBody());
    assertEquals("New payload", sheet.getPayload());
  }

  @Test
  void updatePublished_sheetNotFound() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    UpdateRequest request = new UpdateRequest("testPublisher", "NonExistentSheet", "New payload");

    // Act
    ResponseEntity<String> response = controller.updatePublished(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Sheet not found", response.getBody());
  }

  @Test
  void updatePublished_publisherNotFound() {
    // Arrange
    when(publishers.getPublisherByUsername("nonExistentPublisher")).thenReturn(null);

    UpdateRequest request = new UpdateRequest("nonExistentPublisher", "Sheet1", "New payload");

    // Act
    ResponseEntity<String> response = controller.updatePublished(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Publisher not found", response.getBody());
  }

  @Test
  void updatePublished_noSpreadsheets() {
    // Arrange
    Publisher publisher = new Publisher("testPublisher", new ArrayList<>());
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    UpdateRequest request = new UpdateRequest("testPublisher", "Sheet1", "New payload");

    // Act
    ResponseEntity<String> response = controller.updatePublished(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Sheet not found", response.getBody());
  }
}
