package com.example.huskysheet.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This class stores information about a User and helps store the data
 * in certain columns that will be read by the database.
 *
 * @author Katie w
 */


@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  @Column(name = "role", nullable = false)
  private String role;


  // Default constructor (required by JPA)
  public UserEntity() {}

  // Constructor with parameters
  public UserEntity(String username, String password, boolean enabled, String role) {
    this.username = username;
    this.password = password;
    this.enabled = enabled;
    this.role = role;
  }

  // Getter and setter methods
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
