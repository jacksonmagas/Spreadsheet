package com.example.huskysheet.client.Model;

import com.example.huskysheet.model.Argument;
import com.example.huskysheet.model.Result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultTest {

  private Result result;

  @BeforeEach
  public void setUp() {
    result = new Result();
  }

  @Test
  public void testInitialSuccess() {
    assertFalse(result.isSuccess());
  }

  @Test
  public void testSetSuccess() {
    result.setSuccess(true);
    assertTrue(result.isSuccess());
  }

  @Test
  public void testInitialMessage() {
    assertNull(result.getMessage());
  }

  @Test
  public void testSetMessage() {
    String message = "Test Message";
    result.setMessage(message);
    assertEquals(message, result.getMessage());
  }

  @Test
  public void testInitialValue() {
    assertNull(result.getValue());
  }

  @Test
  public void testSetValue() {
    Argument argument = new Argument();
    argument.setPublisher("admin");
    argument.setSheet("Sheet1");
    argument.setId("1");
    argument.setPayload("Payload");

    result.setValue(Collections.singletonList(argument));

    assertNotNull(result.getValue());
    assertEquals(1, result.getValue().size());
    assertEquals(argument, result.getValue().get(0));
  }

  @Test
  public void testInitialTime() {
    assertEquals(0, result.getTime());
  }

  @Test
  public void testSetTime() {
    long currentTime = System.currentTimeMillis();
    result.setTime(currentTime);
    assertEquals(currentTime, result.getTime());
  }

  @Test
  public void testFullInitialization() {
    boolean success = true;
    String message = "Full Initialization";
    Argument argument = new Argument();
    argument.setPublisher("admin");
    argument.setSheet("Sheet1");
    argument.setId("1");
    argument.setPayload("Payload");

    result.setSuccess(success);
    result.setMessage(message);
    result.setValue(Collections.singletonList(argument));
    result.setTime(System.currentTimeMillis());

    assertTrue(result.isSuccess());
    assertEquals(message, result.getMessage());
    assertNotNull(result.getValue());
    assertEquals(1, result.getValue().size());
    assertEquals(argument, result.getValue().get(0));
    assertNotEquals(0, result.getTime());
  }

  @Test
  public void testNullValues() {
    result.setSuccess(false);
    result.setMessage(null);
    result.setValue(null);
    result.setTime(0);

    assertFalse(result.isSuccess());
    assertNull(result.getMessage());
    assertNull(result.getValue());
    assertEquals(0, result.getTime());
  }
}
