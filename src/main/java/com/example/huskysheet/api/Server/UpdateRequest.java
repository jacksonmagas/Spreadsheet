package com.example.huskysheet.api.Server;

public class UpdateRequest {
  private String publisher;
  private String sheet;
  private String payload;

  public UpdateRequest() {}

  public UpdateRequest(String publisher, String sheet, String payload) {
    this.publisher = publisher;
    this.sheet = sheet;
    this.payload = payload;
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

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }
}
