package com.faketwitter.restlog.handlers;

import com.faketwitter.restlog.model.Message;
import com.faketwitter.restlog.model.User;
import com.faketwitter.restlog.repository.MessageRepository;
import com.faketwitter.restlog.repository.UserRepository;
import com.faketwitter.restlog.repository.projection.LogMessagesOnly;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Component
public class LogMessageHandler {

  private final UserRepository userRepository;
  private final MessageRepository messageRepository;

  @Autowired
  public LogMessageHandler(UserRepository userRepository,
      MessageRepository messageRepository) {
    this.userRepository = userRepository;
    this.messageRepository = messageRepository;
  }

  public List<Message> getUserLogMessagesById(long id) {
    LogMessagesOnly messageCollection = userRepository.findLogMessagesById(id);
    return messageCollection.getLogMessages();
  }

  @Transactional(rollbackFor = {SQLException.class})
  public List<Message> getUserLogMessagesByUsername(String username) {

    Optional<User> user = userRepository.findUserByUsername(username);

    // Check if user exists on user DB
    if(user.isEmpty()){
      String uri = "http://web-user:8080/api/users/name/" + username;
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<User> response = restTemplate.exchange(uri, HttpMethod.GET, null,
          new ParameterizedTypeReference<>() {
          });
      user = Optional.ofNullable(response.getBody());
      if(user.isPresent()){
        userRepository.save(user.get());
      }
    }

    if(user.isPresent()){
      LogMessagesOnly messageCollection = userRepository.findLogMessagesByUsername(username);
      return messageCollection.getLogMessages();
    } else {
      throw new UserNotFoundException("Cannot find user!");
    }
  }

  @Transactional(rollbackFor = {SQLException.class})
  public Message saveLogMessage(String username, String messageBody) {

    Optional<User> user = userRepository.findUserByUsername(username);

    if(user.isEmpty()){
      String uri = "http://web-user:8080/api/users/name/" + username;
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<User> response = restTemplate.exchange(uri, HttpMethod.GET, null,
          new ParameterizedTypeReference<>() {
          });

      user = Optional.ofNullable(response.getBody());
      System.out.println(user.get());
      if(user.isPresent()){
        userRepository.save(user.get());
      }
    }

    if(user.isPresent()){
      User userRef = user.get();
      Message m = new Message();
      m.setMessageBody(messageBody);
      m.setWrittenBy(userRef);

      Message mRef = messageRepository.save(m);
      if(userRef.getLogMessages() == null){
        userRef.setLogMessages(new ArrayList<>());
      }
      userRef.getLogMessages().add(mRef);
      userRepository.save(userRef);

      return mRef;
    } else {
      throw new UserNotFoundException(username);
    }
  }

}
