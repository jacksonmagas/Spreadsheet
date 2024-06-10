package com.example.huskysheet.model;

import java.util.List;

public class Result {
  private boolean success;
  private String message;
  private List<Argument> value;
  private long time;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Argument> getValue() {
    return value;
  }

  public void setValue(List<Argument> value) {
    this.value = value;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
