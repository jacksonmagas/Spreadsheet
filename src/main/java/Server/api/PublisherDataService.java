package Server.api;

import Server.model.Publisher;

import java.util.ArrayList;
import java.util.List;

public class PublisherDataService {

    private List<Publisher> publishers = new ArrayList<>();

    public PublisherDataService() {
        // Mock data for now
        publishers.add(new Publisher("Publisher1", "info@publisher1.com"));
        publishers.add(new Publisher("Publisher2", "info@publisher2.com"));
    }

    public List<Publisher> getPublishers() {
        return new ArrayList<>(publishers);
    }
}