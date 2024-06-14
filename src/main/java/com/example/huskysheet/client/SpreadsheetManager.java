package com.example.huskysheet.client;

import com.example.huskysheet.client.Model.ISpreadsheet;
import com.example.huskysheet.client.Model.ISpreadsheetListener;
import com.example.huskysheet.client.Model.Spreadsheet;
import com.example.huskysheet.client.Utils.Conversions;
import com.example.huskysheet.client.Utils.Coordinate;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import javafx.util.Pair;

/**
 * Class to produce spreadsheets from the remote database and ensure the remote is properly updated.
 * TODO add caching where possible, make methods unblocking where possible, possibly by
 * TODO using futures and returning cached values for now
 */
public class SpreadsheetManager implements ISpreadsheetListener {
    private final HttpClient client;
    private final String authHeader;
    private final String serverUrl;
    private final String userName;
    private int currentID;
    private ISpreadsheet currentSpreadsheet;
    private String currentSheetPublisher;
    private String currentSheetName;
    private CompletableFuture<Result> lastUpdateFuture;
    private List<String> lastCallPublishers;
    private Map<String, List<String>> lastCallSheets;
    private Result lastUpdate;

    /**
     * Create a new spreadsheet manager.
     * @author Jackson Magas
     * @param serverUrl the URL that the server is hosted at, not including the /api/v1
     * @param userName the username to contact the server with
     * @param password the password to contact the server with (username/password are immediately encoded)
     */
    public SpreadsheetManager(String serverUrl, String userName, String password)
        throws URISyntaxException {
        this.client = HttpClient.newHttpClient();
        this.userName = userName;
        this.serverUrl = serverUrl;
        new URI(serverUrl); // test that serverURL is well formed
        authHeader = "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
        lastCallSheets = new HashMap<>();
    }

    public String getUserName() {
        return userName;
    }

    /**
     * Represents all available API endpoints from the server
     */
    private enum Endpoint {
        register, getPublishers, createSheet, getSheets, deleteSheet,
        getUpdatesForSubscription, getUpdatesForPublished, updatePublished, updateSubscription
    }

    /**
     * Represents a result object from an API call
     * @param success if the call was a success
     * @param message failure message if the call failed
     * @param value A list of arguments containing relevant information to answer a request
     */
    private record Result(boolean success, String message, List<Argument> value) {};

    /**
     * Represents an argument to an API call
     * @param publisher the publisher of the relevant sheet
     * @param sheet the name of the relevant sheet
     * @param id the id of the sheet
     * @param payload the list of updates to the sheet
     */
    private record Argument(String publisher, String sheet, String id, String payload) {};

    /**
     * Call the specified endpoint with no argument
     * @author Jackson Magas
     * @param target the endpoint to call
     * @return The result of the API call as a result record
     * @throws JsonSyntaxException if the response is not the correct format
     * @throws IOException if an IO error occurs
     * @throws InterruptedException if the API call is interrupted
     */
    private Result callAPI(Endpoint target)
        throws JsonSyntaxException, IOException, InterruptedException {
        return callAPI(target, null);
    }

    /**
     * Call the specified endpoint with the given argument, or null if no argument is needed
     * @author Jackson Magas
     * @param target the endpoint to call
     * @param arg the argument to call the endpoint with
     * @return The result of the API call as a result record
     * @throws JsonSyntaxException if the response is not the correct format
     * @throws IOException if an IO error occurs
     * @throws InterruptedException if the API call is interrupted
     */
    private Result callAPI(Endpoint target, Argument arg)
        throws JsonSyntaxException, IOException, InterruptedException {
        var request = BuildRequest(target, arg);
        var response = client.send(request, BodyHandlers.ofString());
        return handleResponse(response);
    }

    private CompletableFuture<Result> callAPIAsync(Endpoint target, Argument arg) {
        var request = BuildRequest(target, arg);
        var response = client.sendAsync(request, BodyHandlers.ofString());
        return response.thenApply(r -> {
            try {
                return handleResponse(r);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        });
    }


    private static Result handleResponse(HttpResponse<String> response) throws IOException {
        System.out.println("Response: " + response.toString());
        System.out.println("Response Body: " + response.body());
        switch(response.statusCode()) {
            case 200, 201 -> {
                return new Gson().fromJson(response.body(), Result.class);
            }
            case 401 -> {
                throw new IOException("Request error: " + response.statusCode());
            }
            case 409 -> {
                throw new IOException("Conflict Error: " + response.statusCode());
            }
            case 500 -> {
                throw new IOException("Internal server error: " + response.statusCode());
            }
            default -> {
                throw new IOException("Unexpected status code: " + response);
            }
        }
    }

    private HttpRequest BuildRequest(Endpoint target, Argument arg) {
        var requestBuilder = HttpRequest.newBuilder()
            .header("Authorization", authHeader)
            .header("Content-Type", "application/json");
        try {
            requestBuilder.uri(new URI(serverUrl + "/api/v1/" + target.name()));
        } catch(URISyntaxException ignored) {
            // the uri syntax can't error because serverUrl is checked in the compiler
        }
        switch (target) {
            case register, getPublishers -> {
                requestBuilder.GET();
            }
            case createSheet, getSheets, deleteSheet, getUpdatesForSubscription,
                 getUpdatesForPublished, updatePublished, updateSubscription -> {
                requestBuilder.POST(BodyPublishers.ofString(new Gson().toJson(arg)));
            }
        }
        var request = requestBuilder.build();
        System.out.println("Request: " + request.toString());
        System.out.println("Request Body: " + new Gson().toJson(arg));
        return request;
    }

    /**
     * Register with the server using the username and password
     * @author Jackson Magas
     * @return true if the request is successful
     */
    public boolean register() {
        try {
            return callAPI(Endpoint.register).success();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get a list of publishers on this server.
     * @author Jackson Magas
     * @return A list of all publisher names, or an empty optional if the request fails
     */
    public List<String> getPublishers() throws APICallException {
        try {
            Result result = callAPI(Endpoint.getPublishers);
            if (result.success) {
                lastCallPublishers = result.value().stream()
                    .map(Argument::publisher)
                    .toList();
                return lastCallPublishers;
            } else {
                throw new APICallException(result.message);
            }
        } catch (Exception e) {
            throw new APICallException(e);
        }
    }

    public List<String> tryGetPublishers() throws APICallException {
        try {
            var publishers = callAPIAsync(Endpoint.getPublishers, new Argument("", "", "", ""));
            if (publishers.isDone()) {
                lastCallPublishers = publishers.get().value().stream()
                    .map(Argument::publisher)
                    .toList();
            }
            return lastCallPublishers;
        } catch (Exception e) {
            throw new APICallException(e);
        }
    }

    /**
     * Get a list of available spreadsheets from the server.
     * @author Jackson Magas
     * @return An optional containing the list of sheets if the request was successful, otherwise an
     * empty optional
     */
    public List<String> getAvailableSheets(String publisher) throws APICallException {
        try {
            Result result = callAPI(Endpoint.getSheets, new Argument(publisher, "", "", ""));
            if (result.success()) {
                return result
                    .value().stream()
                    .map(Argument::sheet)
                    .toList();
            } else {
                throw new APICallException(result.message);
            }
        } catch (Exception e) {
            throw new APICallException(e);
        }
    }

    public List<String> tryGetAvailableSheets(String publisher) throws APICallException {
        try {
            var publishers = callAPIAsync(Endpoint.getSheets, new Argument(publisher, "", "", ""));
            if (publishers.isDone()) {
                lastCallSheets.put(publisher, publishers.get().value().stream()
                    .map(Argument::sheet)
                    .toList());
            }
            return lastCallSheets.getOrDefault(publisher, List.of());
        } catch (Exception e) {
            throw new APICallException(e);
        }
    }

    /**
     * Gets a specific spreadsheet from the server and set it as the current spreadsheet.
     * @author Jackson Magas
     * @param sheetName the sheet to get
     * @return An optional containing the spreadsheet if the request was successful, and an
     * empty optional otherwise
     */
    public ISpreadsheet getSpreadsheet(String publisher, String sheetName) throws APICallException {
        try {
            Result result;
            if (publisher.equals(userName)) {
                result = callAPI(Endpoint.getUpdatesForPublished, new Argument(publisher, sheetName, "0", ""));
            } else {
                result = callAPI(Endpoint.getUpdatesForSubscription, new Argument(publisher, sheetName, "0", ""));
            }
            if (result.success) {
                if (currentSpreadsheet != null) {
                    currentSpreadsheet.unregisterListener(this);
                }
                currentSpreadsheet = new Spreadsheet();
                currentSpreadsheet.updateSheet(parsePayload(result.value.getFirst().payload));
                currentSpreadsheet.registerListener(this);
                currentSheetName = sheetName;
                currentID = Integer.parseInt(result.value.getFirst().id());
                currentSheetPublisher = result.value.getFirst().publisher();
                return currentSpreadsheet;
            } else {
                throw new APICallException(result.message);
            }
        } catch (Exception e) {
            throw new APICallException(e);
        }
    }

    /**
     * Parse the string into a list of coordinate update pairs.
     * @author Jackson Magas
     * @param payload the string to parse
     * @return A list of updates to make to a spreadsheet
     * @throws IllegalArgumentException if each line of the string does not start with a reference
     *                                  such as $A1 or $b2
     */
    private List<Pair<Coordinate, String>> parsePayload(String payload) throws IllegalArgumentException {
        if (payload.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(payload.split("\n"))
            .map(s -> s.split(" ", 2))
            .filter(s -> s.length == 2)
            .map(s -> new Pair<>(Conversions.stringToCoordinate(s[0]), s[1]))
            .toList();
    }

    /**
     * Get all updates for the current sheet from the server and apply them to the current spreadsheet.
     * This method blocks until the update is received.
     * @author Jackson Magas
     * @throws APICallException if the API call fails
     */
    public void getUpdates() throws APICallException {
        try {
            tryGetUpdates();
            lastUpdateFuture.get();
            tryGetUpdates();
        } catch (Exception e) {
            throw new APICallException(e);
        }
    }

    /**
     * Check if the result of the last get updates api call has been received, and if it has been
     * received update the current sheet with the results of that call then make a new get updates
     * call.
     * Also makes a new call if there has not been one yet.
     * @return true if the update attempt changed the sheet
     * @throws APICallException If the api call fails, or the future fails with exception.
     * @author Jackson Magas
     */
    public boolean tryGetUpdates() throws APICallException {
        boolean sheetChanged = false;
        if (lastUpdateFuture != null && lastUpdateFuture.isDone()) {
            try {
                var result = lastUpdateFuture.get();
                if (result.success) {
                    sheetChanged = currentSpreadsheet.updateSheet(parsePayload(result.value.getFirst().payload));
                    currentID = Integer.parseInt(result.value.getFirst().id());
                    lastUpdate = result;
                } else {
                    throw new APICallException(result.message);
                }
            } catch (Exception e) {
                throw new APICallException(e);
            }
        }

        if (lastUpdateFuture == null || lastUpdateFuture.isDone()) {
            try {
                if (currentSheetPublisher.equals(userName)) {
                    lastUpdateFuture = callAPIAsync(Endpoint.getUpdatesForPublished,
                        new Argument(userName, currentSheetName, Integer.toString(currentID), ""));
                } else {
                    lastUpdateFuture = callAPIAsync(Endpoint.getUpdatesForSubscription,
                        new Argument(currentSheetPublisher, currentSheetName,
                            Integer.toString(currentID), ""));
                }
            } catch (Exception e) {
                throw new APICallException(e);
            }
        }

        return sheetChanged;
    }

    /**
     * Publish a new spreadsheet with the given name on the server Set the current spreadsheet to
     * that sheet
     *
     * @param sheetName the name of the new sheet to create
     * @return the created sheet
     * @throws APICallException if the API call fails
     * @author Jackson Magas
     */
    public ISpreadsheet createSpreadsheet(String sheetName) throws APICallException {
        try {
            Result result = callAPI(Endpoint.createSheet, new Argument(userName, sheetName, "", ""));
            if (result.success) {
                return getSpreadsheet(userName, sheetName);
            } else {
                throw new APICallException(result.message);
            }
        } catch (Exception e) {
            throw new APICallException(e);
        }
    }

    /**
     * Delete the spreadsheet with the given name from the server.
     *
     * @throws APICallException if the API call fails
     * @author Jackson Magas
     */
    public void deleteSpreadsheet() throws APICallException {
        try {
            Result result = callAPI(Endpoint.deleteSheet, new Argument(currentSheetPublisher, currentSheetName, "", ""));
            if (!result.success) {
                throw new APICallException(result.message);
            }
        } catch (Exception e) {
            throw new APICallException(e);
        }
    }

    /**
     * Update the current sheet on the server based on the new spreadsheet update.
     * @author Jackson Magas
     * @param coordinate the coordinate of the cell to update
     * @param update the string entered by the user in the cell
     *
     * @throws APICallException if the API call to update the sheet fails
     */
    @Override
    public void handleUpdate(Coordinate coordinate, String update) throws Exception {
        try {
            Result result;

            //todo
            if (currentSheetPublisher.equals(userName)) {
                result = callAPI(Endpoint.updatePublished,
                    new Argument(currentSheetPublisher,
                        currentSheetName,
                        Integer.toString(currentID),
                        coordinate.toString() + " " + update));
            } else {
                result = callAPI(Endpoint.updateSubscription,
                    new Argument(currentSheetPublisher,
                        currentSheetName,
                        Integer.toString(currentID),
                        coordinate.toString() + " " + update));
            }
            if (!result.success) {
                throw new APICallException(result.message);
            }
        } catch (Exception e) {
            throw new APICallException(e);
        }
    }
}
