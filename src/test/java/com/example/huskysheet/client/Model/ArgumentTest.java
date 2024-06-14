package com.example.huskysheet.client.Model;

import com.example.huskysheet.model.Argument;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ArgumentTest {

  @Test
  public void testGetAndSetPublisher() {
    Argument argument = new Argument();
    argument.setPublisher("admin");
    assertEquals("admin", argument.getPublisher());
  }

  @Test
  public void testGetandSetSheet() {
    Argument argument = new Argument();
    argument.setSheet("Sheet1");
    assertEquals("Sheet1", argument.getSheet());
  }

  @Test
  public void testGetAndSetId() {
    Argument argument = new Argument();
    argument.setId("1");
    assertEquals("1", argument.getId());
  }

  @Test
  public void testGetAndSetPayload() {
    Argument argument = new Argument();
    argument.setPayload("$A1 1");
    assertEquals("$A1 1", argument.getPayload());
  }

  @Test
  public void testNullPublisher() {
    Argument argument = new Argument();
    argument.setPublisher(null);
    assertNull(argument.getPublisher());
  }

  @Test
  public void testNullSheet() {
    Argument argument = new Argument();
    argument.setSheet(null);
    assertNull(argument.getSheet());
  }

  @Test
  public void testNullId() {
    Argument argument = new Argument();
    argument.setId(null);
    assertNull(argument.getId());
  }

  @Test
  public void testNullPayload() {
    Argument argument = new Argument();
    argument.setPayload(null);
    assertNull(argument.getPayload());
  }

  @Test
  public void testEmptyPublisher() {
    Argument argument = new Argument();
    argument.setPublisher("");
    assertEquals("", argument.getPublisher());
  }

  @Test
  public void testEmptySheet() {
    Argument argument = new Argument();
    argument.setSheet("");
    assertEquals("", argument.getSheet());
  }

  @Test
  public void testEmptyId() {
    Argument argument = new Argument();
    argument.setId("");
    assertEquals("", argument.getId());
  }

  @Test
  public void testEmptyPayload() {
    Argument argument = new Argument();
    argument.setPayload("");
    assertEquals("", argument.getPayload());
  }

  @Test
  public void testLongPublisher() {
    Argument argument = new Argument();
    String longPublisher = "a".repeat(1000);
    argument.setPublisher(longPublisher);
    assertEquals(longPublisher, argument.getPublisher());
  }

  @Test
  public void testLongSheet() {
    Argument argument = new Argument();
    String longSheet = "b".repeat(1000);
    argument.setSheet(longSheet);
    assertEquals(longSheet, argument.getSheet());
  }

  @Test
  public void testLongId() {
    Argument argument = new Argument();
    String longId = "1".repeat(1000);
    argument.setId(longId);
    assertEquals(longId, argument.getId());
  }

  @Test
  public void testLongPayload() {
    Argument argument = new Argument();
    String longPayload = "d".repeat(1000);
    argument.setPayload(longPayload);
    assertEquals(longPayload, argument.getPayload());
  }
}
