package Server.api;

import Server.model.Publisher;
import Server.model.Spreadsheet;
import Server.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PublisherDataService {
    public Map<Publisher, List<Spreadsheet>> publishedSpreadsheets;

    public PublisherDataService() {
        this.publishedSpreadsheets = new HashMap<>();
    }

    public void addPublishedSpreadsheet(Publisher publisher, Spreadsheet spreadsheet) {
        if (publisher == null || spreadsheet == null) {
            throw new IllegalArgumentException("Publisher and spreadsheet cannot be null.");
        }
        if (!publishedSpreadsheets.containsKey(publisher)) {
            publishedSpreadsheets.put(publisher, new ArrayList<>());
        }
        publishedSpreadsheets.get(publisher).add(spreadsheet);
    }

    public Publisher getPublisher(Spreadsheet spreadsheet) {
        for (Map.Entry<Publisher, List<Spreadsheet>> entry : publishedSpreadsheets.entrySet()) {
            List<Spreadsheet> spreadsheets = entry.getValue();
            if (spreadsheets.contains(spreadsheet)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Publisher getPublisherByUsername(String username) {
        for (Publisher publisher : publishedSpreadsheets.keySet()) {
            if (publisher.getName().equals(username)) {
                return publisher;
            }
        }
        return null;
    }
}