package com.faketwitter.restserver.handler;

import com.faketwitter.restserver.error.UserNotFoundException;
import com.faketwitter.restserver.models.Message;
import com.faketwitter.restserver.models.User;
import com.faketwitter.restserver.repositories.MessageRepository;
import com.faketwitter.restserver.repositories.UserRepository;
import com.faketwitter.restserver.repositories.projections.LogMessagesOnly;
import com.faketwitter.restserver.repositories.projections.PrivateMessagesOnly;
import com.faketwitter.restserver.view.UserView;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserHandler {

  private final UserRepository userRepository;
  private final MessageRepository messageRepository;

  @Autowired
  public UserHandler(UserRepository userRepository,
      MessageRepository messageRepository){
    this.userRepository = userRepository;
    this.messageRepository = messageRepository;
  }

  public List<User> getAllUsers(){
    Iterable<User> userIterable = userRepository.findAll();
    List<User> userList = new ArrayList<>();
    for(User user : userIterable){
      userList.add(user);
    }
    return userList;
  }

  /**
   * Gets user by its id.
   * @param id - id of the user
   * @return A user if one exists or null otherwise.
   */
  public Optional<User> getUserById(long id){
    return userRepository.findById(id);
  }

  /**
   * Saves a user to the database.
   * @param user - the user to be saved
   * @return The user if it was saved successfully.
   */
  public User save(User user){
    return userRepository.save(user);
  }

  public List<Message> getUserLogMessagesById(long id) {
    LogMessagesOnly messageCollection = userRepository.findLogMessagesById(id);
    return messageCollection.getLogMessages();
  }

  public List<Message> getUserLogMessagesByUsername(String username) {
    LogMessagesOnly messageCollection = userRepository.findLogMessagesByUsername(username);
    return messageCollection.getLogMessages();
  }

  public List<Message> getUserPrivateMessagesByUsername(String username) {
    PrivateMessagesOnly messageCollection = userRepository.findPrivateMessagesByUsername(username);
    return messageCollection.getPrivateMessages();
  }

  public Optional<User> getUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }

  @Transactional(rollbackFor = {SQLException.class})
  public Message saveLogMessage(String username, String messageBody) {
    Optional<User> user = userRepository.findUserByUsername(username);
    if(user.isPresent()){
      User userRef = user.get();
      Message m = new Message();
      m.setMessageBody(messageBody);
      m.setWrittenBy(userRef);

      Message mRef = messageRepository.save(m);
      userRef.getLogMessages().add(mRef);
      userRepository.save(userRef);

      return mRef;
    } else {
      throw new UserNotFoundException(username);
    }
  }

  @Transactional(rollbackFor = {SQLException.class})
  public Message sendPrivateMessage(String writtenBy, String messageBody, String sendTo) {
    Optional<User> optSender = userRepository.findUserByUsername(writtenBy);
    Optional<User> optRecipient = userRepository.findUserByUsername(sendTo);

    if (optSender.isPresent() && optRecipient.isPresent()) {
      User sender = optSender.get();
      User recipient = optRecipient.get();

      Message m = new Message();
      m.setMessageBody(messageBody);
      m.setWrittenBy(sender);

      Message mRef = messageRepository.save(m);

      recipient.getPrivateMessages().add(mRef);
      userRepository.save(recipient);

      return mRef;
    } else {
      throw new UserNotFoundException("Either sender or recipient does not exist!");
    }
  }

  public UserView createUserViewFromUser(User user){
    return new UserView(
        user.getUsername(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail()
    );
  }

  public Optional<User> authenticateUser(String username, String password) {

    User user = userRepository.findUserByUsername(username).orElseThrow(
        () -> new UserNotFoundException(username)
    );

    if(user.getPassword().equals(password))
      return Optional.of(user);
    else
      return Optional.empty();

  }
}
