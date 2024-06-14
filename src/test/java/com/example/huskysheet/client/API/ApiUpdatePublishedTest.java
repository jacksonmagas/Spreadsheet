package com.example.huskysheet.client.API;

import com.example.huskysheet.api.Server.UpdateRequest;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test the API call 'updatePublished' which is used to send requests to update a certain
 * Spreadsheet that is owned by a certain Publisher.
 *
 * @author Katie Winkleblack
 */

class ApiUpdatePublishedTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void updatePublished() {
    // Arrange mocks
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    //request
    UpdateRequest request = new UpdateRequest("testPublisher", "Sheet1", "New payload");

    // Authentification (bad)
    Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", null);
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    // Action
    ResponseEntity<Result> response = controller.updatePublished(request);

    // Assertion that authentification led to unauthorized response
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }


}
