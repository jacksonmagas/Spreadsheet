package Server.api;

import Server.model.Publisher;

public class CreateSpreadsheet {
    private String sheet;
    private Publisher publisher;

    public CreateSpreadsheet(String sheet, Publisher publisher) {
        this.sheet = sheet;
        this.publisher = publisher;
    }

    public String getSheet() {
        return sheet;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}