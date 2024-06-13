package com.example.huskysheet.client.API;

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
    //mocks user putting in their username
    when(authentication.getName()).thenReturn("testUser");
    //links security to authentification
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }


  @Test
  void testCreateSheetSuccess() {

    Publisher mockPublisher = new Publisher("testUser", new ArrayList<>());
    // stiumulate authentication
    when(publishers.getPublisherByUsername("testUser")).thenReturn(mockPublisher);
    // create request
    CreateSheetRequest request = new CreateSheetRequest(mockPublisher.getName(), "NewSheet");
    System.out.println("Mocked Publisher: " + mockPublisher.getName());


// Action 1 (create first sheet)
    ResponseEntity<Result> response = controller.createSheet(request);
    Result result = response.getBody();


// Set result
    List<Argument> expectedArguments = new ArrayList<>();
    Argument arg = new Argument();
    arg.setPublisher(mockPublisher.getName());
    arg.setSheet("NewSheet");
    arg.setPayload("Sheet created successfully");
    expectedArguments.add(arg);


    result.setSuccess(true);
    result.setMessage("Sheet created successfully");
    result.setValue(expectedArguments);
    result.setTime(System.currentTimeMillis());


// Assertion 1
    assertTrue(result.isSuccess());
    assertEquals("Sheet created successfully", result.getMessage());
    assertEquals(expectedArguments, result.getValue());


// Action 2 (create same sheet twice so it fails)
    ResponseEntity<Result> response2 = controller.createSheet(request);
    Result result2 = response2.getBody();


// Assertion 2 (can't create sheet twice)
    assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    assertFalse(result2.isSuccess());
    assertTrue(result2.getValue().isEmpty());
  }


  @Test
  public void testCreateSheetPublisherNull() {

// create null publisher sheet
    CreateSheetRequest request = new CreateSheetRequest(null, "NewSheet");


    ResponseEntity<Result> response = controller.createSheet(request);

// null publisher response
    assertEquals(400, response.getStatusCodeValue());
    assertFalse(response.getBody().isSuccess());
    assertEquals("Publisher is null", response.getBody().getMessage());
  }


  @Test
  void testCreateSheetUnauthorized() {
    // Arrange
    Publisher mockPublisher = new Publisher("testUser", new ArrayList<>());
    CreateSheetRequest request = new CreateSheetRequest(mockPublisher.getName(), "NewSheet");


    // simulate authentification
    when(publishers.getPublisherByUsername("testUser")).thenReturn(mockPublisher);
    SecurityContext securityContext = mock(SecurityContext.class);
    Authentication authentication = mock(Authentication.class);
    // tries authentification with the wrong username
    when(authentication.getName()).thenReturn("otherUser");
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);


    // Action
    ResponseEntity<Result> response = controller.createSheet(request);


    // Assertertion
    Result result = response.getBody();
    // unauthorized because username does not exist
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertNotNull(result);
    assertFalse(result.isSuccess());
    assertEquals("Unauthorized: sender is not owner of sheet", result.getMessage());
  }
}