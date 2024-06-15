package com.example.huskysheet.api.Server;

/**
 * Represents a request to delete a sheet.
 * This class holds the details of the publisher and the sheet to be deleted.
 *
 * @author Julia Ouritskaya
 */
public class DeleteSheetRequest {
  private String publisher;
  private String sheet;

  /**
   * Default constructor for DeleteSheetRequest.
   */
  public DeleteSheetRequest() {}

  /**
   * Parameterized constructor for DeleteSheetRequest.
   *
   * @param publisher the name of the publisher
   * @param sheet the name or content of the sheet to be deleted
   */
  public DeleteSheetRequest(String publisher, String sheet) {
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
   * @param sheet the name or content of the sheet to be deleted
   */
  public void setSheet(String sheet) {
    this.sheet = sheet;
  }
}