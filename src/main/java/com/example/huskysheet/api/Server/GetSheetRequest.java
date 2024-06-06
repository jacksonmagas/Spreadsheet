package com.example.huskysheet.api.Server;

public class GetSheetRequest {
  private String publisher;

  public GetSheetRequest() {}

  public GetSheetRequest(String publisher) {
    this.publisher = publisher;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }
}
