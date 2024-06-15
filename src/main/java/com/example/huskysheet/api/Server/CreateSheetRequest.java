package com.example.huskysheet.api.Server;

/**
 * Represents a request to create a new sheet.
 * This class holds the details of the publisher and the sheet to be created.
 *
 * @author Julia Ouritskaya
 */
public class CreateSheetRequest {
  private String publisher;
  private String sheet;

  /**
   * Default constructor for CreateSheetRequest.
   */
  public CreateSheetRequest() {
  }

  /**
   * Parameterized constructor for CreateSheetRequest.
   *
   * @param publisher the name of the publisher
   * @param sheet the name or content of the sheet
   */
  public CreateSheetRequest(String publisher, String sheet) {
    this.publisher = publisher;
    this.sheet = sheet;
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
   * Gets the sheet name or content.
   *
   * @return the sheet name or content
   */
  public String getSheet() {
    return sheet;
  }

  /**
   * Sets the sheet name or content.
   *
   * @param sheet the name or content of the sheet
   */
  public void setSheet(String sheet) {
    this.sheet = sheet;
  }
}
