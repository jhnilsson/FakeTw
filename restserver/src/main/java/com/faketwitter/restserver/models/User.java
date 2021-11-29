package com.faketwitter.restserver.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class User {
  @Id
  @GeneratedValue
  private long id;

  @Column(name="username", nullable = false, unique = true)
  private String username;

  @Column(name="password", nullable = false)
  private String password;

  @Column(name="firstname", nullable = false)
  private String firstName;

  @Column(name="lastname", nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  @JoinTable
  @OneToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  private List<Message> logMessages;

  @JoinTable
  @OneToMany
  @JsonIgnore
  private List<Message> privateMessages;

  public User() {
  }

  public User(long id, String username, String password, String firstName, String lastName,
      String email, List<Message> logMessages,
      List<Message> privateMessages) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.logMessages = logMessages;
    this.privateMessages = privateMessages;
  }

  public List<Message> getPrivateMessages() {
    return privateMessages;
  }

  public void setPrivateMessages(List<Message> privateMessages) {
    this.privateMessages = privateMessages;
  }

  public List<Message> getLogMessages() {
    return logMessages;
  }

  public void setLogMessages(List<Message> logMessages) {
    this.logMessages = logMessages;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
