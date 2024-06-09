package com.example.huskysheet.controller;

import com.example.huskysheet.api.Server.CreateSheetRequest;
import com.example.huskysheet.api.Server.DeleteSheetRequest;
import com.example.huskysheet.api.Server.GetSheetRequest;
import com.example.huskysheet.api.Server.GetUpdatesRequest;
import com.example.huskysheet.api.Server.UpdateRequest;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Spreadsheet;
import com.example.huskysheet.model.UpdatePayload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SpreadsheetController {
  private final Publishers publishers;

  public SpreadsheetController() {
    this.publishers = Publishers.getInstance();
  }

  @GetMapping("/register")
  public ResponseEntity<String> register() {
    // Get the authenticated client name from the security context
    String clientName = SecurityContextHolder.getContext().getAuthentication().getName();
    publishers.registerNewPublisher(clientName);
    return new ResponseEntity<>("Registration successful", HttpStatus.OK);
  }

  @GetMapping("/getPublishers")
  public ResponseEntity<List<String>> getPublisher() {
    List<Publisher> allPublishers = publishers.getAllPublishers();
    List<String> publisherNames = new ArrayList<>();

    for (Publisher publisher : allPublishers) {
      publisherNames.add(publisher.getName());
    }

    return new ResponseEntity<>(publisherNames, HttpStatus.OK);
  }

  @PostMapping("/createSheet")
  public ResponseEntity<?> createSheet(@RequestBody CreateSheetRequest request) {
    try {
      // Get the authenticated username
      String clientName = SecurityContextHolder.getContext().getAuthentication().getName();
      // Check if the authenticated user matches the publisher in the request
      if (!request.getPublisher().equals(clientName)) {
        return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
      }

      // Retrieve the publisher by username
      Publisher publisher = publishers.getPublisherByUsername(request.getPublisher());
      if (publisher == null) {
        return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
      }

      // Check if the sheet already exists
      for (Spreadsheet sheet : publisher.getSpreadsheets()) {
        if (sheet.getSheetName().equals(request.getSheet())) {
          return new ResponseEntity<>("Sheet already exists: " + request.getSheet(), HttpStatus.CONFLICT);
        }
      }

      // Create a new sheet and add it to the publisher
      Spreadsheet newSheet = new Spreadsheet(publisher, request.getSheet());
      publisher.addSpreadsheet(newSheet);
      return new ResponseEntity<>("Sheet created successfully", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/getSheets")
  public ResponseEntity<?> getSheets(@RequestBody GetSheetRequest request) {
    String publisherName = request.getPublisher();
    Publisher publisher = publishers.getPublisherByUsername(publisherName);

    if (publisher != null) {
      List<Spreadsheet> spreadsheets = publisher.getSpreadsheets();
      List<String> sheetNames = new ArrayList<>();
      for (Spreadsheet spreadsheet : spreadsheets) {
        sheetNames.add(spreadsheet.getSheetName());
      }
      return new ResponseEntity<>(sheetNames, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/deleteSheet")
  public ResponseEntity<String> deleteSheet(@RequestBody DeleteSheetRequest request) {
    String publisherName = request.getPublisher();
    String sheetName = request.getSheet();

    Publisher publisherObj = publishers.getPublisherByUsername(publisherName);

    if (publisherObj != null) {
      Spreadsheet sheetToRemove = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(sheetName)) {
          sheetToRemove = s;
          break;
        }
      }

      if (sheetToRemove != null) {
        publisherObj.removeSpreadsheet(sheetToRemove);
        return new ResponseEntity<>("Sheet deleted successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Sheet not found", HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/getUpdatesForSubscription")
  public ResponseEntity<?> getUpdatesForSubscription(@RequestBody GetUpdatesRequest request) {
    String publisherName = request.getPublisher();
    String sheetName = request.getSheet();
    String id = request.getId();

    Publisher publisherObj = publishers.getPublisherByUsername(publisherName);

    if (publisherObj != null) {
      Spreadsheet sheetToUpdate = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(sheetName)) {
          sheetToUpdate = s;
          break;
        }
      }

      if (sheetToUpdate != null) {
        List<String> updates = sheetToUpdate.getUpdatesAfterId(id);
        UpdatePayload payload = new UpdatePayload(updates);
        return new ResponseEntity<>(payload, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Sheet not found", HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/getUpdatesForPublished")
  public ResponseEntity<?> getUpdatesForPublished(@RequestBody GetUpdatesRequest request) {
    String publisherName = request.getPublisher();
    String sheetName = request.getSheet();
    String id = request.getId();

    Publisher publisherObj = publishers.getPublisherByUsername(publisherName);

    if (publisherObj != null) {
      Spreadsheet sheetToUpdate = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(sheetName)) {
          sheetToUpdate = s;
          break;
        }
      }

      if (sheetToUpdate != null) {
        List<String> updateRequests = sheetToUpdate.getUpdateRequestsAfterId(id);
        UpdatePayload payload = new UpdatePayload(updateRequests);
        return new ResponseEntity<>(payload, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Sheet not found", HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/updatePublished")
  public ResponseEntity<String> updatePublished(@RequestBody UpdateRequest request) {
    Publisher publisherObj = publishers.getPublisherByUsername(request.getPublisher());

    if (publisherObj != null) {
      Spreadsheet sheetToUpdate = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(request.getSheet())) {
          sheetToUpdate = s;
          break;
        }
      }

      if (sheetToUpdate != null) {
        sheetToUpdate.setPayload(request.getPayload());
        return new ResponseEntity<>("Sheet updated successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Sheet not found", HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
    }
  }


  @PostMapping("/updateSubscription")
  public ResponseEntity<String> updateSubscription(@RequestBody UpdateRequest request) {
    Publisher publisherObj = publishers.getPublisherByUsername(request.getPublisher());

    if (publisherObj != null) {
      Spreadsheet sheetToUpdate = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(request.getSheet())) {
          sheetToUpdate = s;
          break;
        }
      }

      if (sheetToUpdate != null) {
        sheetToUpdate.addUpdateRequest(request.getPayload());
        return new ResponseEntity<>("Subscription updated successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Sheet not found", HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
    }
  }
}