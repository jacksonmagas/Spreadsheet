package com.example.huskysheet.model;

import java.util.List;

/**
 * Represents the payload containing a list of updates for a spreadsheet.
 * Provides methods to get and set the updates.
 * Ensures the updates are properly managed and accessible.
 *
 * @author Julia
 */
public class UpdatePayload {
  private List<String> updates;

  /**
   * Constructs an UpdatePayload with the specified list of updates.
   *
   * @param updates the list of updates
   */
  public UpdatePayload(List<String> updates) {
    this.updates = updates;
  }

  /**
   * Returns the list of updates.
   *
   * @return the list of updates
   */
  public List<String> getUpdates() {
    return updates;
  }

  /**
   * Sets the list of updates.
   *
   * @param updates the list of updates to set
   */
  public void setUpdates(List<String> updates) {
    this.updates = updates;
  }
}
