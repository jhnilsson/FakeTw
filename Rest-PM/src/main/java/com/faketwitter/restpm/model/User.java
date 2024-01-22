package com.faketwitter.restpm.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class User {

  @Id
  private long id;

  @Column(name="username", nullable = false, unique = true)
  private String username;

  @Column(name="firstname", nullable = false)
  private String firstName;

  @Column(name="lastname", nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  @JoinTable
  @OneToMany
  @JsonIgnore
  private List<Message> privateMessages;

  public User() {
  }

  public User(long id, String username, String password, String firstName, String lastName,
      String email, List<Message> privateMessages) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.privateMessages = privateMessages;
  }

  public List<Message> getPrivateMessages() {
    return privateMessages;
  }

  public void setPrivateMessages(List<Message> privateMessages) {
    this.privateMessages = privateMessages;
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

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", privateMessages=" + privateMessages +
        '}';
  }
}
