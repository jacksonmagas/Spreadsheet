package com.example.huskysheet.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publishers {
  private static Publishers instance;
  private Map<Publisher, List<Spreadsheet>> publisherMap;

  private Publishers() {
    publisherMap = new HashMap<>();
  }

  public static Publishers getInstance() {
    if (instance == null) {
      instance = new Publishers();
    }
    return instance;
  }

  public Publisher getPublisherByUsername(String username) {
    for (Publisher publisher : publisherMap.keySet()) {
      if (publisher.getName().equals(username)) {
        return publisher;
      }
    }
    return null;
  }

  public void addPublisher(Publisher publisher) {
    publisherMap.put(publisher, new ArrayList<>());
  }

  public void registerNewPublisher(String clientName) {
    Publisher publisher =  new Publisher(clientName, new ArrayList<>());
    publisherMap.put(publisher, new ArrayList<>());
  }

  public List<Spreadsheet> getSpreadsheetsForPublisher(Publisher publisher) {
    return publisherMap.getOrDefault(publisher, new ArrayList<>());
  }

  public List<Publisher> getAllPublishers() {
    return new ArrayList<>(publisherMap.keySet());
  }
}
