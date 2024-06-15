package com.example.huskysheet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a spreadsheet associated with a publisher.
 * Contains methods for managing published updates and subscription updates.
 * Provides functionality to retrieve updates based on their ID.
 * Ensures that updates and requests are associated with a valid publisher.
 *
 * @author Julia Ouritskaya
 */
public class Spreadsheet {
    private final Publisher publisher;
    private final String spreadsheetName;
    private List<String> updates;
    private List<String> updateRequests;
    private int lastPublishedUpdateId;
    private int lastSubscriptionUpdateId;

    /**
     * Constructs a Spreadsheet with the specified publisher and name.
     * Initializes update lists and counters.
     *
     * @param publisher the publisher associated with the spreadsheet
     * @param spreadsheetName the name of the spreadsheet
     * @throws IllegalArgumentException if the publisher is null
     */
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

    /**
     * Returns the name of the spreadsheet.
     *
     * @return the name of the spreadsheet
     */
    public String getSheetName() {
        return this.spreadsheetName;
    }


    /**
     * Adds a new published update to the spreadsheet.
     * Increments the last published update ID.
     *
     * @param update the update to add
     * @throws NullPointerException if the update is null
     */
    public void addPublishedUpdate(String update) {
        if (update == null) {
            throw new NullPointerException("Update cannot be null");
        }
        lastPublishedUpdateId++;
        this.updates.add(lastPublishedUpdateId + "," + update);
    }

    /**
     * Adds a new subscription update request to the spreadsheet.
     * Increments the last subscription update ID.
     *
     * @param updateRequest the update request to add
     * @throws NullPointerException if the update request is null
     */
    public void addSubscriptionUpdate(String updateRequest) {
        if (updateRequest == null) {
            throw new NullPointerException("Update request cannot be null");
        }
        lastSubscriptionUpdateId++;
        this.updateRequests.add(lastSubscriptionUpdateId + "," + updateRequest);
    }

    /**
     * Retrieves all updates added after the specified ID.
     *
     * @param id the ID after which updates should be retrieved
     * @return a list of updates added after the specified ID
     */
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

    /**
     * Retrieves all subscription update requests added after the specified ID.
     *
     * @param id the ID after which update requests should be retrieved
     * @return a list of update requests added after the specified ID
     */
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

    /**
     * Sets the payload of the spreadsheet by adding a new published update.
     *
     * @param payload the payload to set
     */
    public void setPayload(String payload) {
        this.addPublishedUpdate(payload);
    }

    /**
     * Returns the ID of the last published update.
     *
     * @return the ID of the last published update
     */
    public int getLastPublishedUpdateId() {
        return lastPublishedUpdateId;
    }

    /**
     * Returns the ID of the last subscription update.
     *
     * @return the ID of the last subscription update
     */
    public int getLastSubscriptionUpdateId() {
        return lastSubscriptionUpdateId;
    }
}