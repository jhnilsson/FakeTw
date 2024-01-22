package com.faketwitter.restpm.rest;

import com.faketwitter.restpm.handler.PrivateMessageHandler;
import com.faketwitter.restpm.model.Message;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/private_messages")
public class PrivateMessageController {

  private final PrivateMessageHandler privateMessageHandler;

  @Autowired
  public PrivateMessageController(
      PrivateMessageHandler privateMessageHandler) {
    this.privateMessageHandler = privateMessageHandler;
  }

  /**
   * Gets all private messages belonging to a specific user by name
   * @param username - the user's name
   * @return A list of the users private messages
   */
  @GetMapping("/name/{name}")
  public List<Message> getUserPrivateMessagesByUsername(@PathVariable(name="name", required = true) String username) {
    return privateMessageHandler.getUserPrivateMessagesByUsername(username);
  }

  /**
   * Sends a private message to a different user
   * @param json the custom json to be used to create the message. Requires the params "messageBody",
   *    *             "writtenBy" and "sendTo" in the form of a <String, String> map
   * @return A success message incase it succeeds.
   */
  @PostMapping("/")
  public Message sendPrivateMessage(@RequestBody Map<String, String> json){
    String messageBody = json.get("messageBody");
    String writtenBy = json.get("writtenBy");
    String sendTo = json.get("sendTo");

    return privateMessageHandler.sendPrivateMessage(writtenBy, messageBody, sendTo);

  }

}
