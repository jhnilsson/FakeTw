package com.faketwitter.restserver.handler;

import com.faketwitter.restserver.models.Message;
import com.faketwitter.restserver.repositories.MessageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {

  private final MessageRepository messageRepository;

  @Autowired
  public MessageHandler(MessageRepository messageRepository){
    this.messageRepository = messageRepository;
  }

  /**
   * Gets a message by its id
   * @param id - the messages' id
   * @return The message
   */
  public Optional<Message> getMessage(long id){
    return messageRepository.findById(id);
  }

  /**
   * Creates a new message
   * @param message The message to be created
   */
  public void saveMessage(Message message){
    messageRepository.save(message);
  }

}
