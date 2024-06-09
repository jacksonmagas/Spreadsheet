package com.example.huskysheet.api.Server;

public class CreateSheetRequest {
  private String publisher;
  private String sheet;

  public CreateSheetRequest() {
  }

  public CreateSheetRequest(String publisher, String sheet) {
    this.publisher = publisher;
    this.sheet = sheet;
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
}
