package com.example.huskysheet.model;

/**
 * Model class representing an argument with details about a publisher, sheet, id, and payload.
 * This class is used to transfer data within the application.
 *
 * @autor Julia Ouritskaya
 */
public class Argument {
  private String publisher;
  private String sheet;
  private String id;
  private String payload;

  /**
   * Gets the publisher.
   *
   * @return the publisher
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * Sets the publisher.
   *
   * @param publisher the publisher to set
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  /**
   * Gets the sheet.
   *
   * @return the sheet
   */
  public String getSheet() {
    return sheet;
  }

  /**
   * Sets the sheet.
   *
   * @param sheet the sheet to set
   */
  public void setSheet(String sheet) {
    this.sheet = sheet;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the payload.
   *
   * @return the payload
   */
  public String getPayload() {
    return payload;
  }

  /**
   * Sets the payload.
   *
   * @param payload the payload to set
   */
  public void setPayload(String payload) {
    this.payload = payload;
  }
}
