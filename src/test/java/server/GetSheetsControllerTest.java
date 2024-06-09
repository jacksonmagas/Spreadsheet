package server;

import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Spreadsheet;
import com.example.huskysheet.api.Server.GetSheetRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetSheetsControllerTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getSheets_validPublisher() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    spreadsheets.add(new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1"));
    spreadsheets.add(new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet2"));

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    GetSheetRequest request = new GetSheetRequest("testPublisher");

    // Act
    ResponseEntity<?> response = controller.getSheets(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<String> sheetNames = (List<String>) response.getBody();
    assertEquals(2, sheetNames.size());
    assertEquals("Sheet1", sheetNames.get(0));
    assertEquals("Sheet2", sheetNames.get(1));
  }

  @Test
  void getSheets_publisherNotFound() {
    // Arrange
    when(publishers.getPublisherByUsername(anyString())).thenReturn(null);

    GetSheetRequest request = new GetSheetRequest("nonExistentPublisher");

    // Act
    ResponseEntity<?> response = controller.getSheets(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Publisher not found", response.getBody());
  }

  @Test
  void getSheets_publisherWithNoSheets() {
    // Arrange
    Publisher publisher = new Publisher("testPublisher", Collections.emptyList());
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    GetSheetRequest request = new GetSheetRequest("testPublisher");

    // Act
    ResponseEntity<?> response = controller.getSheets(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<String> sheetNames = (List<String>) response.getBody();
    assertEquals(0, sheetNames.size());
  }
}
