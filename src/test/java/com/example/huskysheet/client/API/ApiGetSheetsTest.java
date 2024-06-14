package com.example.huskysheet.client.API;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Test that the Getsheet method works for when there is no Publisher given
 * @author Katie Winkleblack
 */

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
void testGetSheetsNoPublisher() {

    GetSheetRequest request = new GetSheetRequest("Non existent");

    when(publishers.getPublisherByUsername("Non existent")).thenReturn(null);
    SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("username",null));

    ResponseEntity<Result> response = controller.getSheets(request);
    Result result = response.getBody();

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertFalse(result.isSuccess());
    assertEquals("Publisher not found: Non existent", result.getMessage());
  }
}
