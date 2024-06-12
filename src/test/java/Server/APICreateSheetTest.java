package Server;


import com.example.huskysheet.api.Server.CreateSheetRequest;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class APICreateSheetTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    setupSecurityContext("testUser");
  }

  private void setupSecurityContext(String username) {
    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn(username);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  void createSheet_unauthorizedAccess() {
    // Arrange
    CreateSheetRequest request = new CreateSheetRequest("differentUser", "NewSheet");

    // Act
    ResponseEntity<?> response = controller.createSheet(request);

    // Assert
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void createSheet_publisherNotFound() {
    // Arrange
    CreateSheetRequest request = new CreateSheetRequest("testUser", "NewSheet");
    when(publishers.getPublisherByUsername(anyString())).thenReturn(null);

    // Act
    ResponseEntity<?> response = controller.createSheet(request);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void createSheet_sheetAlreadyExists() {
    // Arrange
    Publisher publisher = new Publisher("testUser", Collections.singletonList(new Spreadsheet(null, "ExistingSheet")));
    CreateSheetRequest request = new CreateSheetRequest("testUser", "ExistingSheet");
    when(publishers.getPublisherByUsername("testUser")).thenReturn(publisher);

    // Act
    ResponseEntity<?> response = controller.createSheet(request);

    // Assert
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  void createSheet_success() {
    // Arrange
    Publisher publisher = new Publisher("testUser", Collections.emptyList());
    CreateSheetRequest request = new CreateSheetRequest("testUser", "NewSheet");
    when(publishers.getPublisherByUsername("testUser")).thenReturn(publisher);

    // Act
    ResponseEntity<?> response = controller.createSheet(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}

