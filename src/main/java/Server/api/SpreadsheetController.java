package Server.api;

import Server.model.Publisher;
import Server.model.Publishers;
import Server.model.Spreadsheet;
import Server.model.UpdatePayload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.parser.Entity;

@RestController
public class SpreadsheetController {
  private final Publishers publishers;

  public SpreadsheetController() {
    this.publishers = Publishers.getInstance();
  }

  @GetMapping("api/v1/register")
  public ResponseEntity<String> register() {
    // Get the authenticated client name from the security context
    String clientName = SecurityContextHolder.getContext().getAuthentication().getName();
    publishers.registerNewPublisher(clientName);

    return new ResponseEntity<>("Registration successful", HttpStatus.OK);
  }

  @GetMapping("api/v1/getPublishers")
  public ResponseEntity<List<String>> getPublisher() {
    List<Publisher> allPublishers = publishers.getAllPublishers();
    List<String> publisherNames = new ArrayList<>();

    for (Publisher publisher : allPublishers) {
      publisherNames.add(publisher.getName());
    }

    return new ResponseEntity<>(publisherNames, HttpStatus.OK);
  }

  @GetMapping("api/v1/getSheets")
  public ResponseEntity<?> getSheets(@RequestParam String publisherName) {
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

  @PostMapping("api/v1/deleteSheet")
  public ResponseEntity<String> deleteSheet(@RequestParam String publisher, @RequestParam String sheet) {
    // Fetch publisher by name
    Publisher publisherObj = publishers.getPublisherByUsername(publisher);

    if (publisherObj != null) {
      // Remove sheet from publisher's spreadsheets
      Spreadsheet sheetToRemove = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheet().equals(sheet)) {
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


  @GetMapping("api/v1/getUpdatesForSubscription")
  public ResponseEntity<?> getUpdatesForSubscription(@RequestParam String publisher, @RequestParam String sheet, @RequestParam String id) {
    // Fetch publisher by name
    Publisher publisherObj = publishers.getPublisherByUsername(publisher);

    if (publisherObj != null) {
      // Check if the sheet exists for the publisher
      Spreadsheet sheetToUpdate = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(sheet)) {
          sheetToUpdate = s;
          break;
        }
      }

      if (sheetToUpdate != null) {
        // Get updates after the given ID
        List<String> updates = sheetToUpdate.getUpdatesAfterId(id);
        // Prepare response
        UpdatePayload payload = new UpdatePayload(updates);
        return new ResponseEntity<>(payload, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Sheet not found", HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("api/v1/getUpdatesForPublished")
  public ResponseEntity<?> getUpdatesForPublished(@RequestParam String publisher, @RequestParam String sheet, @RequestParam String id) {
    // Fetch publisher by name
    Publisher publisherObj = publishers.getPublisherByUsername(publisher);

    if (publisherObj != null) {
      // Check if the sheet exists for the publisher
      Spreadsheet sheetToUpdate = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(sheet)) {
          sheetToUpdate = s;
          break;
        }
      }

      if (sheetToUpdate != null) {
        // Get update requests after the given ID
        List<String> updateRequests = sheetToUpdate.getUpdateRequestsAfterId(id);
        // Prepare response
        UpdatePayload payload = new UpdatePayload(updateRequests);
        return new ResponseEntity<>(payload, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Sheet not found", HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.OK);
    }
  }

  @PostMapping("api/v1/updatePublished")
  public ResponseEntity<String> updatePublished(@RequestParam String publisher, @RequestParam String sheet, @RequestParam String payload) {
    // Fetch publisher by name
    Publisher publisherObj = publishers.getPublisherByUsername(publisher);

    if (publisherObj != null) {
      // Check if the sheet exists for the publisher
      Spreadsheet sheetToUpdate = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(sheet)) {
          sheetToUpdate = s;
          break;
        }
      }

      if (sheetToUpdate != null) {
        // Update the sheet with the provided payload
        sheetToUpdate.setPayload(payload);
        return new ResponseEntity<>("Sheet updated successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Sheet not found", HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("api/v1/updateSubscription")
  public ResponseEntity<String> updateSubscription(@RequestParam String publisher, @RequestParam String sheet, @RequestParam String payload) {
    // Fetch publisher by name
    Publisher publisherObj = publishers.getPublisherByUsername(publisher);

    if (publisherObj != null) {
      // Check if the sheet exists for the publisher
      Spreadsheet sheetToUpdate = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(sheet)) {
          sheetToUpdate = s;
          break;
        }
      }

      if (sheetToUpdate != null) {
        // Update the sheet with the provided payload
        sheetToUpdate.setPayload(payload);
        return new ResponseEntity<>("Subscription updated successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Sheet not found", HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>("Publisher not found", HttpStatus.NOT_FOUND);
    }
  }
}
