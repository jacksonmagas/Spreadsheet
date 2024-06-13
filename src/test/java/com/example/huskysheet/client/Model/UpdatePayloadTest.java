package com.example.huskysheet.client.Model;

import com.example.huskysheet.model.UpdatePayload;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdatePayloadTest {

  @Test
  void testGetUpdates() {
    List<String> updates = Arrays.asList("Update1", "Update2", "Update3");
    UpdatePayload payload = new UpdatePayload(updates);
    assertEquals(updates, payload.getUpdates());
  }

  @Test
  void testSetUpdates() {
    List<String> updates = Arrays.asList("Update1", "Update2", "Update3");
    UpdatePayload payload = new UpdatePayload(updates);

    List<String> newUpdates = Arrays.asList("NewUpdate1", "NewUpdate2");
    payload.setUpdates(newUpdates);
    assertEquals(newUpdates, payload.getUpdates());
  }

  @Test
  void testEmptyUpdates() {
    UpdatePayload payload = new UpdatePayload(Collections.emptyList());
    assertTrue(payload.getUpdates().isEmpty());
  }

  @Test
  void testNullUpdates() {
    UpdatePayload payload = new UpdatePayload(null);
    assertNull(payload.getUpdates());
  }

  @Test
  void testSetNullUpdates() {
    List<String> updates = Arrays.asList("Update1", "Update2", "Update3");
    UpdatePayload payload = new UpdatePayload(updates);

    payload.setUpdates(null);
    assertNull(payload.getUpdates());
  }

  @Test
  void testSetEmptyUpdates() {
    List<String> updates = Arrays.asList("Update1", "Update2", "Update3");
    UpdatePayload payload = new UpdatePayload(updates);

    payload.setUpdates(Collections.emptyList());
    assertTrue(payload.getUpdates().isEmpty());
  }

  @Test
  void testUpdatesWithNullValues() {
    List<String> updates = Arrays.asList("Update1", null, "Update3");
    UpdatePayload payload = new UpdatePayload(updates);
    assertEquals(updates, payload.getUpdates());
  }

  @Test
  void testSetUpdatesWithNullValues() {
    List<String> updates = Arrays.asList("Update1", "Update2", "Update3");
    UpdatePayload payload = new UpdatePayload(updates);

    List<String> newUpdates = Arrays.asList("NewUpdate1", null, "NewUpdate3");
    payload.setUpdates(newUpdates);
    assertEquals(newUpdates, payload.getUpdates());
  }
}
