package com.example.huskysheet.model;

import com.example.huskysheet.model.Publisher;

import java.util.ArrayList;
import java.util.List;

public class Spreadsheet {
    private final Publisher publisher;
    private final String spreadsheetName;
    private List<String> updates;
    private List<String> updateRequests;
    private String payload;

    public Spreadsheet(Publisher publisher, String spreadsheetName) {
        if (publisher == null) {
            throw new IllegalArgumentException("Spreadsheet must have a Publisher");
        }
        this.publisher = publisher;
        this.spreadsheetName = spreadsheetName;
        this.updates = new ArrayList<>();
        this.updateRequests = new ArrayList<>();
        this.payload = "";
    }

    public String getSheetName() {
        return this.spreadsheetName;
    }

    public void addUpdate(String update) {
        this.updates.add(update);
    }

    public List<String> getUpdatesAfterId(String id) {
        List<String> updatesAfterId = new ArrayList<>();
        int lastId = Integer.parseInt(id);
        for (String update : updates) {
            int updateId = Integer.parseInt(update.split(",")[0]);
            if (updateId > lastId) {
                updatesAfterId.add(update);
            }
        }
        return updatesAfterId;
    }

    public void addUpdateRequest(String updateRequest) {
        this.updateRequests.add(updateRequest);
    }

    public List<String> getUpdateRequestsAfterId(String id) {
        List<String> updateRequestsAfterId = new ArrayList<>();
        int lastId = Integer.parseInt(id);
        for (String request : updateRequests) {
            int requestId = Integer.parseInt(request.split(",")[0]);
            if (requestId > lastId) {
                updateRequestsAfterId.add(request);
            }
        }
        return updateRequestsAfterId;
    }

    public void setPayload(String payload) {
        this.payload = payload;
        addUpdate("1," + payload); // Adding payload to updates list with a mock ID
    }

    public String getPayload() {
        return payload;
    }
}
