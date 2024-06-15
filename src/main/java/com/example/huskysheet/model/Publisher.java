package com.example.huskysheet.model;

import java.util.List;

/**
 * Represents a publisher who owns multiple spreadsheets.
 *
 * @author Julia Ouritskaya
 */
public class Publisher {
  private String name;
  private List<Spreadsheet> spreadsheets;

  /**
   * Constructs a Publisher with the given name and list of spreadsheets.
   *
   * @param name         the name of the publisher
   * @param spreadsheets the list of spreadsheets owned by the publisher
   */
  public Publisher(String name, List<Spreadsheet> spreadsheets) {
    this.name = name;
    this.spreadsheets = spreadsheets;
  }

  /**
   * Returns the name of the publisher.
   *
   * @return the name of the publisher
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a new spreadsheet to the list of spreadsheets owned by the publisher.
   *
   * @param spreadsheet the spreadsheet to add
   */
  public void addSpreadsheet(Spreadsheet spreadsheet) {
    this.spreadsheets.add(spreadsheet);
  }

  /**
   * Removes a spreadsheet from the list of spreadsheets owned by the publisher.
   *
   * @param spreadsheet the spreadsheet to remove
   */
  public void removeSpreadsheet(Spreadsheet spreadsheet) {
    if (spreadsheets.contains(spreadsheet)) {
      this.spreadsheets.remove(spreadsheet);
    }
  }

  /**
   * Returns the list of spreadsheets owned by the publisher.
   *
   * @return the list of spreadsheets
   */
  public List<Spreadsheet> getSpreadsheets() {
    return this.spreadsheets;
  }

  /**
   * Sets the list of spreadsheets owned by the publisher.
   *
   * @param sheets1 the list of spreadsheets
   */
  public void setSpreadsheets(List<Spreadsheet> sheets1) {
    this.spreadsheets = sheets1;
  }
}