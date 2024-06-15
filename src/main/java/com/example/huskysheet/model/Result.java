package com.example.huskysheet.model;

import java.util.List;

/**
 * Represents the result of an operation, containing information about its success,
 * a message, the resulting value, and the time of the operation.
 *
 * @author Julia Ouritskaya
 */
public class Result {
  private boolean success;
  private String message;
  private List<Argument> value;
  private long time;

  /**
   * Checks if the operation was successful.
   *
   * @return true if the operation was successful, false otherwise
   */
  public boolean isSuccess() {
    return success;
  }

  /**
   * Sets the success status of the operation.
   *
   * @param success true if the operation was successful, false otherwise
   */
  public void setSuccess(boolean success) {
    this.success = success;
  }

  /**
   * Gets the message associated with the result.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message associated with the result.
   *
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Gets the value resulting from the operation.
   *
   * @return a list of arguments representing the value
   */
  public List<Argument> getValue() {
    return value;
  }

  /**
   * Sets the value resulting from the operation.
   *
   * @param value a list of arguments representing the value
   */
  public void setValue(List<Argument> value) {
    this.value = value;
  }

  /**
   * Gets the time of the operation.
   *
   * @return the time in milliseconds
   */
  public long getTime() {
    return time;
  }

  /**
   * Sets the time of the operation.
   *
   * @param time the time in milliseconds
   */
  public void setTime(long time) {
    this.time = time;
  }
}
