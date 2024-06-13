
package com.example.huskysheet.client.ModelTestingServer;

import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Spreadsheet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestPublishers {

  private Publishers publishers;

  @BeforeEach
  public void setUp() {
    publishers = Publishers.getInstance();
    publishers.addPublisher(new Publisher("TestPublisher", List.of()));
  }

  @Test
  public void testGetPublisherByUsername() {
    Publisher publisher = publishers.getPublisherByUsername("TestPublisher");
    assertNotNull(publisher);
    assertEquals("TestPublisher", publisher.getName());

    publisher = publishers.getPublisherByUsername("NonExistentPublisher");
    assertNull(publisher);
  }

  @Test
  public void testAddPublisher() {
    Publisher newP = new Publisher("NewPublisher", List.of());
    publishers.addPublisher(newP);
    assertEquals(true, publishers.getAllPublishers().contains(newP));
  }


  @Test
  public void testRegisterNewPublisher() {
    publishers.registerNewPublisher("AnotherNewPublisher");
    Publisher pub = publishers.getPublisherByUsername("AnotherNewPublisher");
    assertEquals(true, publishers.getAllPublishers().contains(pub));
  }


  @Test
  public void testgetSpreadsheetsForPublisher() {
    Publisher pub = publishers.getPublisherByUsername("TestPublisher");

    List<Spreadsheet> sheets = publishers.getSpreadsheetsForPublisher(pub);
    sheets.add(new Spreadsheet(new Publisher("testPublisher",
            sheets), "Sheet1"));
    assertEquals(1, sheets.size());
  }

  @Test
  public void testgetAllpublishers() {
    List<Publisher> pubs = publishers.getAllPublishers();
    assertEquals(4, pubs.size());
    assertEquals("NewPublisher", pubs.get(0).getName());
  }

}

