package API;

import com.example.huskysheet.api.Server.GetSheetRequest;
import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Result;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ApiGetSheetsTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    System.out.println("Mocks initialized");
  }

  @Test
  void testGetSheets() {
    // Arrange
    List<Publisher> mockPublishers = new ArrayList<>();
    Publisher publisher1 = new Publisher("Publisher1", new ArrayList<>());
    Publisher publisher2 = new Publisher("Publisher2", new ArrayList<>());
    mockPublishers.add(publisher1);
    mockPublishers.add(publisher2);

    // Mock sheets for each publisher
    List<Spreadsheet> sheets1 = new ArrayList<>();
    sheets1.add(new Spreadsheet(publisher1, "Sheet1"));
    sheets1.add(new Spreadsheet(publisher2, "Sheet2"));
    publisher1.setSpreadsheets(sheets1);

    List<Spreadsheet> sheets2 = new ArrayList<>();
    sheets2.add(new Spreadsheet(publisher2, "Sheet3"));
    publisher2.setSpreadsheets(sheets2);

    when(publishers.getAllPublishers()).thenReturn(mockPublishers);
    System.out.println("Mocked Publishers: " + publishers.getAllPublishers());

    // Act
    GetSheetRequest request = new GetSheetRequest(publisher2.getName());
    ResponseEntity<Result> responseEntity = controller.getSheets(request);
    Result result = responseEntity.getBody();

    // Assert...
    assertNotNull(result);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

  }
}
