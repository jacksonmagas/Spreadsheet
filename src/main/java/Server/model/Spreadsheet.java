package Server.model;

import java.util.ArrayList;
import java.util.List;

public class Spreadsheet {

    private final Publisher publisher;
    private final String spreadsheetName;
    private List<String> updates; // Store updates as strings
    private List<String> updateRequests; // Store update requests as strings
    private String payload;

    public Spreadsheet(Publisher publisher, String spreadsheetName) {
        if (publisher == null) {
            throw new IllegalArgumentException("Spreadsheet must have a Publisher");
        }
        this.publisher = publisher;
        this.spreadsheetName = spreadsheetName;
        this.updates = new ArrayList<>();
        this.updateRequests = new ArrayList<>();
        this.payload = ""; // Initialize payload
    }

    public String getSheetName() {
        return this.spreadsheetName;
    }

    public void addUpdate(String update) {
        this.updates.add(update);
    }

    public List<String> getUpdatesAfterId(String id) {
        // Assuming updates are stored in chronological order
        List<String> updatesAfterId = new ArrayList<>();
        int lastId = Integer.parseInt(id);
        for (String update : updates) {
            // Assuming update string contains an ID at the beginning
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
        // Assuming update requests are stored in chronological order
        List<String> updateRequestsAfterId = new ArrayList<>();
        int lastId = Integer.parseInt(id);
        for (String request : updateRequests) {
            // Assuming update request string contains an ID at the beginning
            int requestId = Integer.parseInt(request.split(",")[0]);
            if (requestId > lastId) {
                updateRequestsAfterId.add(request);
            }
        }
        return updateRequestsAfterId;
    }

    // Setter method for payload
    public void setPayload(String payload) {
        this.payload = payload;
    }

    // Getter method for payload
    public String getPayload() {
        return payload;
    }

    public Spreadsheet getSheet() {
        return this;
    }
}
