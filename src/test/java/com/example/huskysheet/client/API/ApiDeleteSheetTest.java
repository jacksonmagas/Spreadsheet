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

/**
 * Test that the API call 'deleteSheet' works with exceptions
 * @author Katie Winkleblack
 */

class ApiDeleteSheetTest {

  @InjectMocks
  private SpreadsheetController controller;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testDeleteSheet_NullPublisher() {
    // create sheet where the publisher is null
    DeleteSheetRequest request = new DeleteSheetRequest();
    request.setPublisher(null);

    // delete sheet with a null publisher
    ResponseEntity<Result> responseEntity = controller.deleteSheet(request);

    // asserts that the request was bad because null publisher
    assert responseEntity != null;
    assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
  }
}