package com.example.huskysheet.client;

import com.example.huskysheet.client.SpreadsheetManager;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestAPICalls {
    private static final String PROF_SERVER_URL = "https://husksheets.fly.dev";
    private static final String USERNAME = "team4";
    private static final String PASSWORD = "IgUraDn4(kS>_-7>";
    private static SpreadsheetManager spreadsheetManager;

    @BeforeAll
    public static void init() throws URISyntaxException {
        spreadsheetManager = new SpreadsheetManager(PROF_SERVER_URL, USERNAME, PASSWORD);
    }

    @Test
    public void testRegister() {
        Assertions.assertDoesNotThrow(() -> {spreadsheetManager.register();});
    }
}
