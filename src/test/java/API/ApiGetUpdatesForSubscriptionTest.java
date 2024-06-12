package API;


import com.example.huskysheet.api.Server.GetUpdatesRequest;
import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Spreadsheet;

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
import static org.mockito.Mockito.when;

class ApiGetUpdatesForSubscriptionTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
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
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}

