
package com.example.huskysheet.client.API;

import com.example.huskysheet.api.Server.GetUpdatesRequest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

/**
 * Test the API call 'getUpdatesForPublisher' which is used to to retrieve updates
 * for a published Spreadsheet
 *
 * @author Katie Winkleblack
 */

class ApiGetUpdatesForPublishedTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getUpdatesForPublished() {
    // Arrange mocks
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets), "Sheet1");
    sheet.addPublishedUpdate("2,Request1") ;
    sheet.addPublishedUpdate("3,Request2") ;
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    // Request updates
    GetUpdatesRequest request = new GetUpdatesRequest("testPublisher", "Sheet1", "1");

    // null authentification so that we can test not found
    Authentication authentication = new UsernamePasswordAuthenticationToken("testPublisher", null);
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    // Action
    ResponseEntity<Result> response = controller.getUpdatesForPublished(request);

    // Assertertion
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    Result result = response.getBody();
    assertFalse(result.isSuccess());
  }
}