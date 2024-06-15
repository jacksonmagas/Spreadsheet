package com.example.huskysheet.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class managing a collection of publishers and their associated spreadsheets.
 * This class provides methods to register new publishers, retrieve publishers by username,
 * and manage spreadsheets for each publisher.
 *
 * @author Julia Ouritskaya
 */
public class Publishers {
  private static Publishers instance;
  private Map<Publisher, List<Spreadsheet>> publisherMap;

  /**
   * Private constructor to prevent instantiation.
   */
  private Publishers() {
    publisherMap = new HashMap<>();
  }

  /**
   * Returns the singleton instance of Publishers.
   *
   * @return the singleton instance
   */
  public static Publishers getInstance() {
    if (instance == null) {
      instance = new Publishers();
    }
    return instance;
  }

  /**
   * Retrieves a publisher by their username.
   *
   * @param username the username of the publisher
   * @return the publisher with the specified username, or null if not found
   */
  public Publisher getPublisherByUsername(String username) {
    for (Publisher publisher : publisherMap.keySet()) {
      if (publisher.getName().equals(username)) {
        return publisher;
      }
    }
    return null;
  }

  /**
   * Adds a new publisher to the collection.
   *
   * @param publisher the publisher to add
   */
  public void addPublisher(Publisher publisher) {
    publisherMap.put(publisher, new ArrayList<>());
  }

  /**
   * Registers a new publisher if they do not already exist in the collection.
   *
   * @param clientName the name of the client to register as a publisher
   */
  public void registerNewPublisher(String clientName) {
    if (getPublisherByUsername(clientName) == null) {
      Publisher publisher = new Publisher(clientName, new ArrayList<>());
      publisherMap.put(publisher, new ArrayList<>());
      System.out.println("Publisher registered: " + clientName);
    }
  }

  /**
   * Retrieves the list of spreadsheets associated with a publisher.
   *
   * @param publisher the publisher whose spreadsheets to retrieve
   * @return the list of spreadsheets for the specified publisher
   */
  public List<Spreadsheet> getSpreadsheetsForPublisher(Publisher publisher) {
    return publisherMap.getOrDefault(publisher, new ArrayList<>());
  }

  /**
   * Retrieves all publishers in the collection.
   *
   * @return a list of all publishers
   */
  public List<Publisher> getAllPublishers() {
    return new ArrayList<>(publisherMap.keySet());
  }

  /**
   * Prints the names of all publishers in the collection to the console.
   */
  public void printPublishers() {
    for (Publisher publisher : publisherMap.keySet()) {
      System.out.println("Publisher: " + publisher.getName());
    }
  }
}

