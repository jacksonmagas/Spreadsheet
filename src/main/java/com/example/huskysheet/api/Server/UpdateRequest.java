package com.example.huskysheet.api.Server;

/**
 * Represents a request to update a specific sheet.
 * This class holds the details of the publisher, sheet, and the payload of the update.
 *
 * @author Julia Ouritskaya
 */
public class UpdateRequest {
  private String publisher;
  private String sheet;
  private String payload;

  /**
   * Default constructor for UpdateRequest.
   */
  public UpdateRequest() {}

  /**
   * Parameterized constructor for UpdateRequest.
   *
   * @param publisher the name of the publisher
   * @param sheet the name of the sheet
   * @param payload the payload of the update
   */
  public UpdateRequest(String publisher, String sheet, String payload) {
    this.publisher = publisher;
    this.sheet = sheet;
    this.payload = payload;
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

  /**
   * Gets the sheet name.
   *
   * @return the sheet name
   */
  public String getSheet() {
    return sheet;
  }

  /**
   * Sets the sheet name.
   *
   * @param sheet the name of the sheet
   */
  public void setSheet(String sheet) {
    this.sheet = sheet;
  }

  /**
   * Gets the payload of the update.
   *
   * @return the update payload
   */
  public String getPayload() {
    return payload;
  }

  /**
   * Sets the payload of the update.
   *
   * @param payload the update payload
   */
  public void setPayload(String payload) {
    this.payload = payload;
  }
}
