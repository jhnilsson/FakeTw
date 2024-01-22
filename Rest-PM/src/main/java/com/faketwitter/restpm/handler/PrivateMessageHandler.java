package com.faketwitter.restpm.handler;

import com.faketwitter.restpm.model.Message;
import com.faketwitter.restpm.model.User;
import com.faketwitter.restpm.repository.MessageRepository;
import com.faketwitter.restpm.repository.projections.PrivateMessagesOnly;
import com.faketwitter.restpm.repository.UserRepository;
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
public class PrivateMessageHandler {

  private final UserRepository userRepository;
  private final MessageRepository messageRepository;

  @Autowired
  public PrivateMessageHandler(UserRepository userRepository,
      MessageRepository messageRepository) {
    this.userRepository = userRepository;
    this.messageRepository = messageRepository;
  }

  @Transactional(rollbackFor = {SQLException.class})
  public Message sendPrivateMessage(String writtenBy, String messageBody, String sendTo) {
    Optional<User> optSender = userRepository.findUserByUsername(writtenBy);
    Optional<User> optRecipient = userRepository.findUserByUsername(sendTo);

    if(optSender.isEmpty()){
      optSender = checkUser(writtenBy, optSender);
    }
    if(optRecipient.isEmpty()){
      optRecipient = checkUser(sendTo, optRecipient);
    }

    if (optSender.isPresent() && optRecipient.isPresent()) {
      User sender = optSender.get();
      User recipient = optRecipient.get();

      Message m = new Message();
      m.setMessageBody(messageBody);
      m.setWrittenBy(sender);

      Message mRef = messageRepository.save(m);

      if(recipient.getPrivateMessages() == null){
        recipient.setPrivateMessages(new ArrayList<>());
      }
      recipient.getPrivateMessages().add(mRef);
      userRepository.save(recipient);

      return mRef;
    } else {
      throw new UserNotFoundException("Either sender or recipient does not exist!");
    }
  }

  private Optional<User> checkUser(String username, Optional<User> optUser) {
    if(optUser.isEmpty()){
      String uri = "http://web-user:8080/api/users/name/" + username;
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<User> response = restTemplate.exchange(uri, HttpMethod.GET, null,
          new ParameterizedTypeReference<>() {
          });

      optUser = Optional.ofNullable(response.getBody());
      System.out.println(optUser.get());
      if(optUser.isPresent()){
        userRepository.save(optUser.get());
      }
    }
    return optUser;
  }

  public List<Message> getUserPrivateMessagesByUsername(String username) {

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
      PrivateMessagesOnly messageCollection = userRepository.findPrivateMessagesByUsername(username);
      return messageCollection.getPrivateMessages();
    } else {
      throw new UserNotFoundException("Cannot find user!");
    }


  }

}
