package com.example.huskysheet.api.Server;

public class GetUpdatesRequest {
  private String publisher;
  private String sheet;
  private String id;

  public GetUpdatesRequest() {}

  public GetUpdatesRequest(String publisher, String sheet, String id) {
    this.publisher = publisher;
    this.sheet = sheet;
    this.id = id;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getSheet() {
    return sheet;
  }

  public void setSheet(String sheet) {
    this.sheet = sheet;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}