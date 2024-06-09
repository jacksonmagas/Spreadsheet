import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Spreadsheet;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPublisher {

  @Test
  public void publisherName() {
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    spreadsheets.add(new Spreadsheet(new Publisher("testPublisher",
            spreadsheets), "Sheet1"));
    spreadsheets.add(new Spreadsheet(new Publisher("testPublisher",
            spreadsheets), "Sheet2"));

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    assertEquals("testPublisher", publisher.getName());
  }
}
