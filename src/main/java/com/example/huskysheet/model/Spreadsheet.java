package com.example.huskysheet.model;

import java.util.ArrayList;
import java.util.List;

public class Spreadsheet {
    private final Publisher publisher;
    private final String spreadsheetName;
    private List<String> updates;
    private List<String> updateRequests;
    private int lastPublishedUpdateId;
    private int lastSubscriptionUpdateId;

    public Spreadsheet(Publisher publisher, String spreadsheetName) {
        if (publisher == null) {
            throw new IllegalArgumentException("Spreadsheet must have a Publisher");
        }
        this.publisher = publisher;
        this.spreadsheetName = spreadsheetName;
        this.updates = new ArrayList<>();
        this.updateRequests = new ArrayList<>();
        this.lastPublishedUpdateId = 0;
        this.lastSubscriptionUpdateId = 0;
    }

    public String getSheetName() {
        return this.spreadsheetName;
    }

    public void addPublishedUpdate(String update) {
        lastPublishedUpdateId++;
        this.updates.add(lastPublishedUpdateId + "," + update);
    }

    public void addSubscriptionUpdate(String updateRequest) {
        lastSubscriptionUpdateId++;
        this.updateRequests.add(lastSubscriptionUpdateId + "," + updateRequest);
    }

    public List<String> getUpdatesAfterId(String id) {
        List<String> updatesAfterId = new ArrayList<>();
        int lastId = Integer.parseInt(id);
        for (String update : updates) {
            String[] parts = update.split(",", 2);
            int updateId = Integer.parseInt(parts[0]);
            if (updateId > lastId) {
                updatesAfterId.add(update);
            }
        }
        return updatesAfterId;
    }

    public List<String> getUpdateRequestsAfterId(String id) {
        List<String> updateRequestsAfterId = new ArrayList<>();
        int lastId = Integer.parseInt(id);
        for (String request : updateRequests) {
            String[] parts = request.split(",", 2);
            int requestId = Integer.parseInt(parts[0]);
            if (requestId > lastId) {
                updateRequestsAfterId.add(request);
            }
        }
        return updateRequestsAfterId;
    }

    public void setPayload(String payload) {
        this.addPublishedUpdate(payload);
    }

    public int getLastPublishedUpdateId() {
        return lastPublishedUpdateId;
    }

    public int getLastSubscriptionUpdateId() {
        return lastSubscriptionUpdateId;
    }
}
