package server;

import Server.api.PublisherDataService;
import Server.model.Publisher;
import Server.model.Spreadsheet;
import Server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class PublisherDataServiceTest {

        private PublisherDataService publisherDataService;
        private Publisher publisher;
        private Spreadsheet spreadsheet;

        @BeforeEach
        void setUp() {
            publisherDataService = new PublisherDataService();
            publisher = mock(Publisher.class);
            spreadsheet = mock(Spreadsheet.class);
        }

        @Test
        void testAddPublishedSpreadsheet() {
            publisherDataService.addPublishedSpreadsheet(publisher, spreadsheet);
            List<Spreadsheet> spreadsheets = publisherDataService.publishedSpreadsheets.get(publisher);
            assertEquals(1, spreadsheets.size());
            assertEquals(spreadsheet, spreadsheets.get(0));
        }
    @Test
    void testGetPublisher() {
            publisher = new Publisher("Adam", "fancy@gmail");
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "user1@example.com"));
        users.add(new User("user2", "password2", "user2@example.com"));
            spreadsheet = new Spreadsheet(publisher, users, "mock", 3, 5);
        publisherDataService.addPublishedSpreadsheet(publisher, spreadsheet);
        Publisher retrievedPublisher = publisherDataService.getPublisher(spreadsheet);
        assertEquals("Adam", retrievedPublisher.getName());
    }

    @Test
    void testGetPublisherWithUnpublishedSpreadsheet() {
        Publisher retrievedPublisher = publisherDataService.getPublisher(spreadsheet);
        assertNull(retrievedPublisher);
    }
    }
