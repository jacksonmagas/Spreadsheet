package Server;

import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Spreadsheet;
import com.example.huskysheet.api.Server.DeleteSheetRequest;

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

class APIDeleteSheetTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void deleteSheet_successfulDeletion() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet1 = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    spreadsheets.add(sheet1);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    DeleteSheetRequest request = new DeleteSheetRequest("testPublisher", "Sheet1");

    // Act
    ResponseEntity<String> response = controller.deleteSheet(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Sheet deleted successfully", response.getBody());
    verify(publishers, times(1)).getPublisherByUsername("testPublisher");
    assertEquals(0, publisher.getSpreadsheets().size());
  }

  @Test
  void deleteSheet_sheetNotFound() {
    // Arrange
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet1 = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    spreadsheets.add(sheet1);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    DeleteSheetRequest request = new DeleteSheetRequest("testPublisher", "Sheet2");

    // Act
    ResponseEntity<String> response = controller.deleteSheet(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Sheet not found", response.getBody());
  }

  @Test
  void deleteSheet_publisherNotFound() {
    // Arrange
    when(publishers.getPublisherByUsername("nonExistentPublisher")).thenReturn(null);

    DeleteSheetRequest request = new DeleteSheetRequest("nonExistentPublisher", "Sheet1");

    // Act
    ResponseEntity<String> response = controller.deleteSheet(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Publisher not found", response.getBody());
  }
}
