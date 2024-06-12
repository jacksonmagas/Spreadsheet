package com.example.huskysheet.controller;


import com.example.huskysheet.api.Server.CreateSheetRequest;
import com.example.huskysheet.api.Server.DeleteSheetRequest;
import com.example.huskysheet.api.Server.GetSheetRequest;
import com.example.huskysheet.api.Server.GetUpdatesRequest;
import com.example.huskysheet.api.Server.UpdateRequest;
import com.example.huskysheet.model.Argument;
import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Result;
import com.example.huskysheet.model.Spreadsheet;


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
  public Result register() {
    // Get the authenticated client name from the security context
    String clientName = SecurityContextHolder.getContext().getAuthentication().getName();
    publishers.registerNewPublisher(clientName);


    Result result = new Result();
    result.setSuccess(true);
    result.setMessage(null);
    result.setValue(new ArrayList<>());
    result.setTime(System.currentTimeMillis());


    return result;
  }


  @GetMapping("/getPublishers")
  public Result getPublisher() {
    List<Publisher> allPublishers = publishers.getAllPublishers();
    List<Argument> publisherArguments = new ArrayList<>();


    for (Publisher publisher : allPublishers) {
      Argument arg = new Argument();
      arg.setPublisher(publisher.getName());
      arg.setSheet(null);
      arg.setId(null);
      arg.setPayload(null);
      publisherArguments.add(arg);
      System.out.println("Publisher in list: " + publisher.getName());
    }


    Result result = new Result();
    result.setSuccess(true);
    result.setMessage(null);
    result.setValue(publisherArguments);
    result.setTime(System.currentTimeMillis());


    return result;
  }


  @PostMapping("/createSheet")
  public ResponseEntity<Result> createSheet(@RequestBody CreateSheetRequest request) {
    Result result = new Result();


    if (request.getPublisher() == null) {
      result.setSuccess(false);
      result.setMessage("Publisher is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    if (request.getSheet() == null) {
      result.setSuccess(false);
      result.setMessage("Sheet is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    try {
      // Get the authenticated username
      String clientName = SecurityContextHolder.getContext().getAuthentication().getName();
      // Check if the authenticated user matches the publisher in the request
      if (!request.getPublisher().equals(clientName)) {
        result.setSuccess(false);
        result.setMessage("Unauthorized: sender is not owner of sheet");
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
      }


      // Retrieve the publisher by username
      Publisher publisher = publishers.getPublisherByUsername(request.getPublisher());
      if (publisher == null) {
        result.setSuccess(false);
        result.setMessage("Unauthorized: sender is not owner of sheet");
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
      }


      // Check if the sheet already exists
      for (Spreadsheet sheet : publisher.getSpreadsheets()) {
        if (sheet.getSheetName().equals(request.getSheet())) {
          result.setSuccess(false);
          result.setMessage("Sheet already exists: " + request.getSheet());
          result.setValue(new ArrayList<>());
          result.setTime(System.currentTimeMillis());
          return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
      }


      // Create a new sheet and add it to the publisher
      Spreadsheet newSheet = new Spreadsheet(publisher, request.getSheet());
      publisher.addSpreadsheet(newSheet);
      result.setSuccess(true);
      result.setMessage(null);
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @PostMapping("/getSheets")
  public ResponseEntity<Result> getSheets(@RequestBody GetSheetRequest request) {
    Result result = new Result();


    if (request.getPublisher() == null) {
      result.setSuccess(false);
      result.setMessage("Publisher is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    Publisher publisher = publishers.getPublisherByUsername(request.getPublisher());


    if (publisher != null) {
      List<Spreadsheet> spreadsheets = publisher.getSpreadsheets();
      List<Argument> arguments = new ArrayList<>();
      for (Spreadsheet spreadsheet : spreadsheets) {
        Argument arg = new Argument();
        arg.setPublisher(request.getPublisher());
        arg.setSheet(spreadsheet.getSheetName());
        arguments.add(arg);
      }
      result.setSuccess(true);
      result.setMessage(null);
      result.setValue(arguments);
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.OK);
    } else {
      result.setSuccess(false);
      result.setMessage("Publisher not found: " + request.getPublisher());
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
  }


  @PostMapping("/deleteSheet")
  public ResponseEntity<Result> deleteSheet(@RequestBody DeleteSheetRequest request) {
    Result result = new Result();


    if (request.getPublisher() == null) {
      result.setSuccess(false);
      result.setMessage("Publisher is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    if (request.getSheet() == null) {
      result.setSuccess(false);
      result.setMessage("Sheet is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    try {
      // Get the authenticated username
      String clientName = SecurityContextHolder.getContext().getAuthentication().getName();


      Publisher publisherObj = publishers.getPublisherByUsername(request.getPublisher());


      if (!request.getPublisher().equals(clientName)) {
        result.setSuccess(false);
        result.setMessage("Unauthorized: sender is not owner of sheet");
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
      }


      Spreadsheet sheetToRemove = null;
      for (Spreadsheet s : publisherObj.getSpreadsheets()) {
        if (s.getSheetName().equals(request.getSheet())) {
          sheetToRemove = s;
          break;
        }
      }


      if (sheetToRemove != null) {
        publisherObj.removeSpreadsheet(sheetToRemove);
        result.setSuccess(true);
        result.setMessage(null);
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.OK);
      } else {
        result.setSuccess(false);
        result.setMessage("Sheet does not exist: " + request.getSheet());
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @PostMapping("/getUpdatesForSubscription")
  public ResponseEntity<Result> getUpdatesForSubscription(@RequestBody GetUpdatesRequest request) {
    Result result = new Result();

    if (request.getPublisher() == null) {
      result.setSuccess(false);
      result.setMessage("Publisher is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    if (request.getSheet() == null) {
      result.setSuccess(false);
      result.setMessage("Sheet is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    if (request.getId() == null) {
      result.setSuccess(false);
      result.setMessage("Id is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

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
        StringBuilder combinedPayload = new StringBuilder();
        int lastId = Integer.parseInt(id);

        for (String update : updates) {
          String[] parts = update.split(",", 2);
          combinedPayload.append(parts[1]).append("\n");
          lastId = Integer.parseInt(parts[0]);
        }

        Argument arg = new Argument();
        arg.setPublisher(publisherName);
        arg.setSheet(sheetName);
        arg.setId(String.valueOf(lastId));
        arg.setPayload(combinedPayload.toString());

        result.setSuccess(true);
        result.setMessage(null);
        result.setValue(List.of(arg));
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.OK);
      } else {
        result.setSuccess(false);
        result.setMessage("Not found: " + publisherName + "/" + sheetName);
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
      }
    } else {
      result.setSuccess(false);
      result.setMessage("Not found: " + publisherName + "/" + sheetName);
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/getUpdatesForPublished")
  public ResponseEntity<Result> getUpdatesForPublished(@RequestBody GetUpdatesRequest request) {
    Result result = new Result();

    if (request.getPublisher() == null) {
      result.setSuccess(false);
      result.setMessage("Publisher is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    if (request.getSheet() == null) {
      result.setSuccess(false);
      result.setMessage("Sheet is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    if (request.getId() == null) {
      result.setSuccess(false);
      result.setMessage("Id is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    String clientName = SecurityContextHolder.getContext().getAuthentication().getName();
    String publisherName = request.getPublisher();
    String sheetName = request.getSheet();
    String id = request.getId();

    if (!publisherName.equals(clientName)) {
      result.setSuccess(false);
      result.setMessage("Unauthorized: sender is not owner of sheet");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }

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
        StringBuilder combinedPayload = new StringBuilder();
        int lastId = Integer.parseInt(id);

        for (String update : updateRequests) {
          String[] parts = update.split(",", 2);
          combinedPayload.append(parts[1]).append("\n");
          lastId = Integer.parseInt(parts[0]);
        }

        Argument arg = new Argument();
        arg.setPublisher(publisherName);
        arg.setSheet(sheetName);
        arg.setId(String.valueOf(lastId));
        arg.setPayload(combinedPayload.toString());

        result.setSuccess(true);
        result.setMessage(null);
        result.setValue(List.of(arg));
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.OK);
      } else {
        result.setSuccess(false);
        result.setMessage("Not found: " + publisherName + "/" + sheetName);
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
      }
    } else {
      result.setSuccess(false);
      result.setMessage("Not found: " + publisherName + "/" + sheetName);
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/updatePublished")
  public ResponseEntity<Result> updatePublished(@RequestBody UpdateRequest request) {
    Result result = new Result();

    if (request.getPublisher() == null) {
      result.setSuccess(false);
      result.setMessage("Publisher is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    if (request.getSheet() == null) {
      result.setSuccess(false);
      result.setMessage("Sheet is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    if (request.getPayload() == null) {
      result.setSuccess(false);
      result.setMessage("Payload is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    String clientName = SecurityContextHolder.getContext().getAuthentication().getName();
    String publisherName = request.getPublisher();
    String sheetName = request.getSheet();
    String payload = request.getPayload();

    if (!publisherName.equals(clientName)) {
      result.setSuccess(false);
      result.setMessage("Unauthorized: sender is not owner of sheet");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }

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
        sheetToUpdate.addPublishedUpdate(payload);
        result.setSuccess(true);
        result.setMessage(null);
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.OK);
      } else {
        result.setSuccess(false);
        result.setMessage("Not found: " + publisherName + "/" + sheetName);
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
      }
    } else {
      result.setSuccess(false);
      result.setMessage("Not found: " + publisherName + "/" + sheetName);
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
  }


  @PostMapping("/updateSubscription")
  public ResponseEntity<Result> updateSubscription(@RequestBody UpdateRequest request) {
    Result result = new Result();

    if (request.getPublisher() == null) {
      result.setSuccess(false);
      result.setMessage("Publisher is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    if (request.getSheet() == null) {
      result.setSuccess(false);
      result.setMessage("Sheet is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    if (request.getPayload() == null) {
      result.setSuccess(false);
      result.setMessage("Payload is null");
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    String publisherName = request.getPublisher();
    String sheetName = request.getSheet();
    String payload = request.getPayload();

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
        sheetToUpdate.addSubscriptionUpdate(payload);
        result.setSuccess(true);
        result.setMessage(null);
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.OK);
      } else {
        result.setSuccess(false);
        result.setMessage("Not found: " + publisherName + "/" + sheetName);
        result.setValue(new ArrayList<>());
        result.setTime(System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
      }
    } else {
      result.setSuccess(false);
      result.setMessage("Not found: " + publisherName + "/" + sheetName);
      result.setValue(new ArrayList<>());
      result.setTime(System.currentTimeMillis());
      return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
  }
}