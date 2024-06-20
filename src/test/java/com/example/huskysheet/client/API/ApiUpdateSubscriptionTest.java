package com.example.huskysheet.client.API;


/**
 * Test the API call 'updateSubscription' which requests to update a subscription for
 * a Spreadsheet owned by a certain Publisher.
 *
 * @author Katie Winkleblack
 */


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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ApiUpdateSubscriptionTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void updateSubscription_sheetNotFound() {
    // Arrange mocks
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    Spreadsheet sheet = new Spreadsheet(new Publisher("testPublisher", spreadsheets),
            "Sheet1");
    spreadsheets.add(sheet);

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    // request
    UpdateRequest request = new UpdateRequest("testPublisher",
            "NonExistentSheet", "New subscription payload");

    // Action
    ResponseEntity<Result> response = controller.updateSubscription(request);

    // Asserttion that sheet not found
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void updateSubscription_publisherNotFound() {
    // Arrange mocks
    when(publishers.getPublisherByUsername("nonExistentPublisher")).thenReturn(null);

    //request
    UpdateRequest request = new UpdateRequest("nonExistentPublisher", "Sheet1",
            "New subscription payload");

    // Action
    ResponseEntity<Result> response = controller.updateSubscription(request);

    // Assertion that username of publisher does not exist
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void updateSubscription_noSpreadsheets() {
    // Arrange
    Publisher publisher = new Publisher("testPublisher", new ArrayList<>());
    when(publishers.getPublisherByUsername("testPublisher")).thenReturn(publisher);

    //request
    UpdateRequest request = new UpdateRequest("testPublisher",
            "Sheet1", "New subscription payload");

    // Action
    ResponseEntity<Result> response = controller.updateSubscription(request);

    // Assertion that sheet1 was not found
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}

