package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Server.api.PublisherDataService;
import Server.model.Publisher;

import java.util.Arrays;
import java.util.List;

public class PublisherDataTest {

    private PublisherDataService publisherDataService;

    @BeforeEach
    public void setUp() {
        publisherDataService = Mockito.mock(PublisherDataService.class);
    }

    @Test
    public void testRetrievePublisherData() {
        Publisher publisher1 = new Publisher("Publisher1", "info@publisher1.com");
        Publisher publisher2 = new Publisher("Publisher2", "info@publisher2.com");

        List<Publisher> publishers = Arrays.asList(publisher1, publisher2);
        when(publisherDataService.getPublishers()).thenReturn(publishers);

        List<Publisher> result = publisherDataService.getPublishers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Publisher1", result.get(0).getName());
        assertEquals("info@publisher1.com", result.get(0).getEmail()); // Corrected
        assertEquals("Publisher2", result.get(1).getName());
        assertEquals("info@publisher2.com", result.get(1).getEmail()); // Corrected
    }
}