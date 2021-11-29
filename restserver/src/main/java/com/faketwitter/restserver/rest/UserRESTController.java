package com.faketwitter.restserver.rest;

import com.faketwitter.restserver.error.UserNotFoundException;
import com.faketwitter.restserver.handler.UserHandler;
import com.faketwitter.restserver.models.Message;
import com.faketwitter.restserver.models.User;
import com.faketwitter.restserver.view.UserView;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

  @GetMapping("/log/{id}")
  public List<Message> getUserLogMessages(@PathVariable(name="id", required = true) long id) { return userHandler.getUserLogMessagesById(id); }

  @GetMapping("/log/name/{name}")
  public List<Message> getUserLogMessagesByUsername(@PathVariable(name="name", required = true) String username) { return userHandler.getUserLogMessagesByUsername(username); }

  @GetMapping("/messages/name/{name}")
  public List<Message> getUserPrivateMessagesByUsername(@PathVariable(name="name", required = true) String username) {
    return userHandler.getUserPrivateMessagesByUsername(username);
  }

  @PostMapping("/log/add/byname")
  public String saveLogMessage(@RequestBody Map<String, String> json){
    String messageBody = json.get("messageBody");
    String writtenBy = json.get("writtenBy");

    userHandler.saveLogMessage(writtenBy, messageBody);

    return "Success!";
  }

  @PostMapping("/messages/add/byname")
  public String sendPrivateMessage(@RequestBody Map<String, String> json){
    String messageBody = json.get("messageBody");
    String writtenBy = json.get("writtenBy");
    String sendTo = json.get("sendTo");

    userHandler.sendPrivateMessage(writtenBy, messageBody, sendTo);

    return "Success!";
  }

  @GetMapping("/{id}")
  public UserView getUserById(@PathVariable(name="id", required = true) long id){
    return userHandler.createUserViewFromUser(
        userHandler.getUserById(id).orElseThrow(
            () -> new UserNotFoundException(id)
        )
    );
  }

  @GetMapping("/name/{name}")
  public UserView getUserByUsername(@PathVariable(name="name", required = true) String name){
    return userHandler.createUserViewFromUser(
        userHandler.getUserByUsername(name).orElseThrow(
            () -> new UserNotFoundException(name)
        )
    );
  }

  @Transactional
  @PostMapping("")
  public UserView saveUser(@RequestBody User user){
    return userHandler.createUserViewFromUser(
        userHandler.save(user)
    );


  }

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
