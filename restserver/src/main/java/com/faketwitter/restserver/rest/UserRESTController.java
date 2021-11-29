package com.faketwitter.restserver.rest;

import com.faketwitter.restserver.error.UserNotFoundException;
import com.faketwitter.restserver.handler.UserHandler;
import com.faketwitter.restserver.models.Message;
import com.faketwitter.restserver.models.User;
import com.faketwitter.restserver.view.UserView;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserRESTController {

  private final UserHandler userHandler;

  @Autowired
  public UserRESTController(UserHandler userHandler) {
    this.userHandler = userHandler;
  }

  /**
   * Gets all log messages belonging to a specific user by id
   * @param id - the user's id
   * @return A list of the users log messages
   */
  @GetMapping("/log/{id}")
  public List<Message> getUserLogMessages(@PathVariable(name="id", required = true) long id) { return userHandler.getUserLogMessagesById(id); }

  /**
   * Gets all log messages belonging to a specific user by name
   * @param username - the user's name
   * @return A list of the users log messages
   */
  @GetMapping("/log/name/{name}")
  public List<Message> getUserLogMessagesByUsername(@PathVariable(name="name", required = true) String username) { return userHandler.getUserLogMessagesByUsername(username); }

  /**
   * Gets all private messages belonging to a specific user by name
   * @param username - the user's name
   * @return A list of the users private messages
   */
  @GetMapping("/messages/name/{name}")
  public List<Message> getUserPrivateMessagesByUsername(@PathVariable(name="name", required = true) String username) {
    return userHandler.getUserPrivateMessagesByUsername(username);
  }

  /**
   * Saves a new message to a user's personal log.
   * @param json - the custom json to be used to create the message. Requires the params "messageBody"
   *             and "writtenBy" in the form of a <String, String> map
   * @return A successful message in case of success
   */
  @PostMapping("/log/add/byname")
  public Message saveLogMessage(@RequestBody Map<String, String> json){
    String messageBody = json.get("messageBody");
    String writtenBy = json.get("writtenBy");

    return userHandler.saveLogMessage(writtenBy, messageBody);
  }

  /**
   * Sends a private message to a different user
   * @param json the custom json to be used to create the message. Requires the params "messageBody",
   *    *             "writtenBy" and "sendTo" in the form of a <String, String> map
   * @return A success message incase it succeeds.
   */
  @PostMapping("/messages/add/byname")
  public Message sendPrivateMessage(@RequestBody Map<String, String> json){
    String messageBody = json.get("messageBody");
    String writtenBy = json.get("writtenBy");
    String sendTo = json.get("sendTo");

    return userHandler.sendPrivateMessage(writtenBy, messageBody, sendTo);

  }

  /**
   * Gets a user by their id
   * @param id - the user's id
   * @return A view of the user
   */
  @GetMapping("/{id}")
  public UserView getUserById(@PathVariable(name="id", required = true) long id){
    return userHandler.createUserViewFromUser(
        userHandler.getUserById(id).orElseThrow(
            () -> new UserNotFoundException(id)
        )
    );
  }

  /**
   * Gets user by their username
   * @param name - the user's username
   * @return A view of the user
   */
  @GetMapping("/name/{name}")
  public UserView getUserByUsername(@PathVariable(name="name", required = true) String name){
    return userHandler.createUserViewFromUser(
        userHandler.getUserByUsername(name).orElseThrow(
            () -> new UserNotFoundException(name)
        )
    );
  }

  /**
   * Creates a new user
   * @param user - the user to be created
   * @return A view of the created user
   */
  @Transactional
  @PostMapping("")
  public UserView saveUser(@RequestBody User user){
    return userHandler.createUserViewFromUser(
        userHandler.save(user)
    );


  }

  /**
   * Authenticates a user
   * @param json - a <String, String> map containing json params for "username" and "password"
   * @return A view of the user if successful
   */
  @PostMapping("/login")
  @ResponseBody
  public UserView authenticateUser(@RequestBody Map<String, String> json) {
    String username = json.get("username");
    String password = json.get("password");

    return userHandler.createUserViewFromUser(
        userHandler.authenticateUser(username, password)
        .orElseThrow(
            () -> new UserNotFoundException(username)
        ));
  }


}
