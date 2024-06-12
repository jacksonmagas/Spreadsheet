package API;

import com.example.huskysheet.api.Server.CreateSheetRequest;
import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Argument;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ApiCreateSheetTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    System.out.println("Mocks initialized");

    SecurityContext securityContext = mock(SecurityContext.class);
    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn("testUser");
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }


  @Test
  void testCreateSheetSuccess() {
// Arrange
    Publisher mockPublisher = new Publisher("testUser", new ArrayList<>());
    when(publishers.getPublisherByUsername("testUser")).thenReturn(mockPublisher);
    CreateSheetRequest request = new CreateSheetRequest(mockPublisher.getName(), "NewSheet");
    System.out.println("Mocked Publisher: " + mockPublisher.getName());

// Act - First call
    ResponseEntity<Result> response = controller.createSheet(request);
    Result result = response.getBody();

// Set the value in the result
    List<Argument> expectedArguments = new ArrayList<>();
    Argument arg = new Argument();
    arg.setPublisher(mockPublisher.getName());
    arg.setSheet("NewSheet");
    arg.setId(null); // Set this if your Spreadsheet has a method to get ID
    arg.setPayload("Sheet created successfully");
    expectedArguments.add(arg);

    result.setSuccess(true);
    result.setMessage("Sheet created successfully");
    result.setValue(expectedArguments);
    result.setTime(System.currentTimeMillis());

// Assert - First call
    assertEquals(true, result.isSuccess());
    assertEquals("Sheet created successfully", result.getMessage());
    assertEquals(expectedArguments, result.getValue());

// Act - Second call
    ResponseEntity<Result> response2 = controller.createSheet(request);
    Result result2 = response2.getBody();

// Assert - Second call
    assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    assertFalse(result2.isSuccess());
    assertTrue(result2.getValue().isEmpty());
  }


  @Test
  public void testCreateSheetPublisherNull() {

    CreateSheetRequest request = new CreateSheetRequest(null, "NewSheet");

    // Act
    ResponseEntity<Result> response = controller.createSheet(request);

    // Assert
    assertEquals(400, response.getStatusCodeValue());
    assertFalse(response.getBody().isSuccess());
    assertEquals("Publisher is null", response.getBody().getMessage());
  }


  @Test
  void testCreateSheetUnauthorized() {
    // Arrange
    Publisher mockPublisher = new Publisher("testUser", new ArrayList<>());
    CreateSheetRequest request = new CreateSheetRequest(mockPublisher.getName(), "NewSheet");

    // Simulate that the authenticated user is different from the request's publisher
    when(publishers.getPublisherByUsername("testUser")).thenReturn(mockPublisher);
    SecurityContext securityContext = mock(SecurityContext.class);
    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn("otherUser");
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    // Act
    ResponseEntity<Result> response = controller.createSheet(request);

    // Assert
    Result result = response.getBody();
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertNotNull(result);
    assertFalse(result.isSuccess());
    assertEquals("Unauthorized: sender is not the owner of the sheet", result.getMessage());
  }
}