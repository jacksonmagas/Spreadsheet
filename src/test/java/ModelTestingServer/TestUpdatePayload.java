package ModelTestingServer;

import com.example.huskysheet.model.UpdatePayload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUpdatePayload {

  private UpdatePayload updatePayload;

  @BeforeEach
  public void setUp() {
    List<String> updates = new ArrayList<>();
    updates.add("update1");
    updates.add("update2");
    updates.add("update3");
    updatePayload = new UpdatePayload(updates);
    System.out.println("setUp() executed");
  }

  @Test
  public void testGetUpdates() {
    assertNotNull(updatePayload, "updatePayload is null");
    List<String> updates = updatePayload.getUpdates();
    assertNotNull(updates, "updates list is null");
    assertEquals(3, updates.size(), "updates list size is not 3");
    System.out.println("testGetUpdates() executed");
  }

  @Test
  public void testSetUpdates() {
    List<String> newUpdates = new ArrayList<>();
    newUpdates.add("New Update 1");
    updatePayload.setUpdates(newUpdates);

    List<String> updates = updatePayload.getUpdates();
    assertEquals(1, updates.size());
}
}
