package Server.model;

import java.util.List;

public class UpdatePayload {
  private List<String> updates;

  public UpdatePayload(List<String> updates) {
    this.updates = updates;
  }

  public List<String> getUpdates() {
    return updates;
  }

  public void setUpdates(List<String> updates) {
    this.updates = updates;
  }
}
