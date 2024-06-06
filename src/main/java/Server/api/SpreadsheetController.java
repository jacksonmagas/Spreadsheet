package Server.api;

import Server.model.Publisher;
import Server.model.Publishers;
import Server.model.Spreadsheet;
import Server.model.UpdatePayload;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

@RestController
public class SpreadsheetController {
  private final Publishers publishers;

  public SpreadsheetController() {
    this.publishers = Publishers.getInstance();
  }


  @GetMapping("api/v1/getSheets")
  public Response getSheets(@RequestParam String publisherName) {
    Publisher publisher = publishers.getPublisherByUsername(publisherName);

    if (publisher != null) {
      List<Spreadsheet> spreadsheets = publisher.getSpreadsheets();

      List<String> sheetNames = new ArrayList<>();
      for (Spreadsheet spreadsheet : spreadsheets) {
        sheetNames.add(spreadsheet.getSheetName());
      }

      return Response.status(Response.Status.OK).entity("Sheets retrieved successfully: " + sheetNames).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity("Publisher not found").build();
    }
  }

  @PostMapping("api/v1/deleteSheet")
  public Response deleteSheet(@RequestParam String publisher, @RequestParam String sheet) {
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
        return Response.status(Response.Status.OK).entity("Sheet deleted successfully").build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).entity("Sheet not found").build();
      }
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity("Publisher not found").build();
    }
  }


  @GetMapping("api/v1/getUpdatesForSubscription")
  public Response getUpdatesForSubscription(@RequestParam String publisher, @RequestParam String sheet, @RequestParam String id) {
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

        return Response.status(Response.Status.OK).entity("Updates retrieved successfully" + payload).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).entity("Sheet not found").build();
      }
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity("Publisher not found").build();
    }
  }

  @GetMapping("api/v1/getUpdatesForPublished")
  public Response getUpdatesForPublished(@RequestParam String publisher, @RequestParam String sheet, @RequestParam String id) {
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

        return Response.status(Response.Status.OK).entity("Update requests retrieved successfully" + payload).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).entity("Sheet not found").build();
      }
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity("Publisher not found").build();
    }
  }

  @PostMapping("api/v1/updatePublished")
  public Response updatePublished(@RequestParam String publisher, @RequestParam String sheet, @RequestParam String payload) {
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

        return Response.status(Response.Status.OK).entity("Sheet updated successfully").build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).entity("Sheet not found").build();
      }
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity("Publisher not found").build();
    }
  }

  @PostMapping("api/v1/updateSubscription")
  public Response updateSubscription(@RequestParam String publisher, @RequestParam String sheet, @RequestParam String payload) {
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

        return Response.status(Response.Status.OK).entity("Subscription updated successfully").build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).entity("Sheet not found").build();
      }
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity("Publisher not found").build();
    }
  }
}
