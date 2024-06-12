package ModelTestingServer;


import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Spreadsheet;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class testPublisher {

  @Test
  public void testOne() {
    List<Spreadsheet> spreadsheets = new ArrayList<>();
    spreadsheets.add(new Spreadsheet(new Publisher("testPublisher",
            spreadsheets), "Sheet1"));
    spreadsheets.add(new Spreadsheet(new Publisher("testPublisher",
            spreadsheets), "Sheet2"));

    Publisher publisher = new Publisher("testPublisher", spreadsheets);
    assertEquals("testPublisher", publisher.getName());
    assertEquals(spreadsheets, publisher.getSpreadsheets());

    // tests addSpreadsheet Function
   Spreadsheet newS =  new Spreadsheet(new Publisher("testPublisher", spreadsheets),
            "sheet3");
    publisher.addSpreadsheet(newS);
    assertEquals(true, publisher.getSpreadsheets().contains(newS));

    // test removeSpreadsheet Function
    publisher.removeSpreadsheet(newS);
    assertEquals(false, publisher.getSpreadsheets().contains(newS));
  }


}

