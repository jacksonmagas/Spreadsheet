package com.example.huskysheet.api.Server;

/**
 * Represents a request to get updates for a specific sheet.
 * This class holds the details of the publisher, sheet, and the update ID.
 *
 * @autor Julia Ouritskaya
 */
public class GetUpdatesRequest {
  private String publisher;
  private String sheet;
  private String id;

  /**
   * Default constructor for GetUpdatesRequest.
   */
  public GetUpdatesRequest() {}

  /**
   * Parameterized constructor for GetUpdatesRequest.
   *
   * @param publisher the name of the publisher
   * @param sheet the name of the sheet
   * @param id the update ID
   */
  public GetUpdatesRequest(String publisher, String sheet, String id) {
    this.publisher = publisher;
    this.sheet = sheet;
    this.id = id;
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
   * Gets the update ID.
   *
   * @return the update ID
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the update ID.
   *
   * @param id the update ID
   */
  public void setId(String id) {
    this.id = id;
  }
}