package com.example.huskysheet.client.Model;

import com.example.huskysheet.model.Publisher;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Spreadsheet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublishersTest {

  private Publishers publishers;

  @BeforeEach
  public void setUp() {
    // Using reflection to reset the singleton instance for isolated testing
    try {
      java.lang.reflect.Field instance = Publishers.class.getDeclaredField("instance");
      instance.setAccessible(true);
      instance.set(null, null);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    publishers = Publishers.getInstance();
  }

  @Test
  public void testGetInstance() {
    assertNotNull(publishers);
    assertEquals(publishers, Publishers.getInstance());
  }

  @Test
  public void testAddPublisher() {
    Publisher publisher = new Publisher("admin", new ArrayList<>());
    publishers.addPublisher(publisher);
    assertEquals(publisher, publishers.getPublisherByUsername("admin"));
  }

  @Test
  public void testGetPublisherByUsername() {
    Publisher publisher = new Publisher("admin", new ArrayList<>());
    publishers.addPublisher(publisher);
    Publisher retrievedPublisher = publishers.getPublisherByUsername("admin");
    assertNotNull(retrievedPublisher);
    assertEquals("admin", retrievedPublisher.getName());
  }

  @Test
  public void testGetPublisherByUsernameNonExistent() {
    assertNull(publishers.getPublisherByUsername("nonexistent"));
  }

  @Test
  public void testRegisterNewPublisher() {
    publishers.registerNewPublisher("newPublisher");
    Publisher registeredPublisher = publishers.getPublisherByUsername("newPublisher");
    assertNotNull(registeredPublisher);
    assertEquals("newPublisher", registeredPublisher.getName());
    assertTrue(registeredPublisher.getSpreadsheets().isEmpty());
  }

  @Test
  public void testRegisterNewPublisherAlreadyExists() {
    Publisher publisher = new Publisher("admin", new ArrayList<>());
    publishers.addPublisher(publisher);
    publishers.registerNewPublisher("admin");
    assertEquals(1, publishers.getAllPublishers().size());
  }

  @Test
  public void testGetSpreadsheetsForPublisher() {
    Publisher publisher = new Publisher("admin", new ArrayList<>());
    publishers.addPublisher(publisher);
    Spreadsheet spreadsheet = new Spreadsheet(publisher, "Sheet1");
    publisher.addSpreadsheet(spreadsheet);
    List<Spreadsheet> spreadsheets = publishers.getSpreadsheetsForPublisher(publisher);
    assertEquals(1, spreadsheets.size());
    assertTrue(spreadsheets.contains(spreadsheet));
  }

  @Test
  public void testGetSpreadsheetsForNonExistentPublisher() {
    Publisher nonExistentPublisher = new Publisher("nonexistent", new ArrayList<>());
    List<Spreadsheet> spreadsheets = publishers.getSpreadsheetsForPublisher(nonExistentPublisher);
    assertTrue(spreadsheets.isEmpty());
  }

  @Test
  public void testGetAllPublishers() {
    Publisher publisher1 = new Publisher("admin1", new ArrayList<>());
    Publisher publisher2 = new Publisher("admin2", new ArrayList<>());
    publishers.addPublisher(publisher1);
    publishers.addPublisher(publisher2);
    List<Publisher> allPublishers = publishers.getAllPublishers();
    assertEquals(2, allPublishers.size());
    assertTrue(allPublishers.contains(publisher1));
    assertTrue(allPublishers.contains(publisher2));
  }

  @Test
  public void testPrintPublishers() {
    Publisher publisher1 = new Publisher("admin1", new ArrayList<>());
    Publisher publisher2 = new Publisher("admin2", new ArrayList<>());
    publishers.addPublisher(publisher1);
    publishers.addPublisher(publisher2);
    assertDoesNotThrow(() -> publishers.printPublishers());
  }
}
