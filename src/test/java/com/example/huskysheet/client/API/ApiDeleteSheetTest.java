package com.example.huskysheet.client.API;

import com.example.huskysheet.api.Server.DeleteSheetRequest;
import com.example.huskysheet.controller.SpreadsheetController;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiDeleteSheetTest {

  @InjectMocks
  private SpreadsheetController controller;

  @Mock
  private Publishers publishers;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testDeleteSheet_NullPublisher() {
    // Start request with null publisher
    DeleteSheetRequest request = new DeleteSheetRequest();
    request.setPublisher(null); // Set publisher to null

    // Perform the deleteSheet operation on null publisher
    ResponseEntity<Result> responseEntity = controller.deleteSheet(request);

    // Verify that the controller returned a bad request status
    assert responseEntity != null;
    assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
  }

    @Test
    void testDeleteSheetInternalServerError() {
      // Mock dependencies
      Publishers publishers = mock(Publishers.class);
      Publisher publisher = mock(Publisher.class);
      when(publishers.getPublisherByUsername(anyString())).thenReturn(publisher);

      // Create request
      DeleteSheetRequest request = new DeleteSheetRequest();
      request.setPublisher("username");
      request.setSheet("sheetName");

      // Stimulate authentification
      String clientName = "username"; // Change this to the desired username
      SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(clientName, null));

      // Call request
      ResponseEntity<Result> response = controller.deleteSheet(request);

      // Verify the result
      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
  }

