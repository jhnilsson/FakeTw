package com.faketwitter.restserver.handler;

import com.faketwitter.restserver.error.UserNotFoundException;
import com.faketwitter.restserver.models.User;
import com.faketwitter.restserver.repositories.UserRepository;
import com.faketwitter.restserver.view.UserView;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserHandler {

  private final UserRepository userRepository;

  @Autowired
  public UserHandler(UserRepository userRepository){
    this.userRepository = userRepository;
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

  public Optional<User> getUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }

  public UserView createUserViewFromUser(User user){
    return new UserView(
        user.getId(),
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
