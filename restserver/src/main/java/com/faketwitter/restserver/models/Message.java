package com.faketwitter.restserver.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.springframework.stereotype.Component;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class Message {
  @Id
  @GeneratedValue
  private long id;

  @Column(name="body")
  private String messageBody;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "writtenby", referencedColumnName = "id")
  private User writtenBy;

  public Message() {
  }

  public Message(long id, String messageBody, User writtenBy) {
    this.id = id;
    this.messageBody = messageBody;
    this.writtenBy = writtenBy;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
  }

  public User getWrittenBy() {
    return writtenBy;
  }

  public void setWrittenBy(User writtenBy) {
    this.writtenBy = writtenBy;
  }
}
