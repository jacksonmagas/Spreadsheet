package com.example.huskysheet.api.Server;

/**
 * Represents a request to get a sheet.
 * This class holds the details of the publisher requesting the sheet.
 *
 * @author Julia Ouritskaya
 */
public class GetSheetRequest {
  private String publisher;

  /**
   * Default constructor for GetSheetRequest.
   */
  public GetSheetRequest() {}

  /**
   * Parameterized constructor for GetSheetRequest.
   *
   * @param publisher the name of the publisher
   */
  public GetSheetRequest(String publisher) {
    this.publisher = publisher;
  }

  /**
   * Gets the publisher of the sheet.
   *
   * @return the publisher name
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * Sets the publisher of the sheet.
   *
   * @param publisher the name of the publisher
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }
}
