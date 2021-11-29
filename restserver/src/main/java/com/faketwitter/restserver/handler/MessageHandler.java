package com.faketwitter.restserver.handler;

import com.faketwitter.restserver.models.Message;
import com.faketwitter.restserver.models.User;
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

  public Optional<Message> getMessage(long id){
    return messageRepository.findById(id);
  }

  public List<Message> getAllMessages() {
    List<Message> messages = new ArrayList<>();
    Iterable<Message> messageIter = messageRepository.findAll();

    for(Message m : messageIter){
      messages.add(m);
    }

    return messages;
  }

  public void saveMessage(Message message){
    messageRepository.save(message);
  }

}
