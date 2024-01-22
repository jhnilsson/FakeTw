package com.faketwitter.restlog.rest;

import com.faketwitter.restlog.handlers.LogMessageHandler;
import com.faketwitter.restlog.model.Message;
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
@RequestMapping("/api/log_messages")
public class LogMessageController {

  private final LogMessageHandler logMessageHandler;

  @Autowired
  public LogMessageController(LogMessageHandler logMessageHandler) {
    this.logMessageHandler = logMessageHandler;
  }

  /**
   * Gets all log messages belonging to a specific user by id
   * @param id - the user's id
   * @return A list of the users log messages
   */
  @GetMapping("/{id}")
  public List<Message> getUserLogMessages(@PathVariable(name="id", required = true) long id) { return logMessageHandler.getUserLogMessagesById(id); }

  /**
   * Gets all log messages belonging to a specific user by name
   * @param username - the user's name
   * @return A list of the users log messages
   */
  @GetMapping("/name/{name}")
  public List<Message> getUserLogMessagesByUsername(@PathVariable(name="name", required = true) String username) { return logMessageHandler.getUserLogMessagesByUsername(username); }

  /**
   * Saves a new message to a user's personal log.
   * @param json - the custom json to be used to create the message. Requires the params "messageBody"
   *             and "writtenBy" in the form of a <String, String> map
   * @return A successful message in case of success
   */
  @PostMapping("/")
  public Message saveLogMessage(@RequestBody Map<String, String> json){
    String messageBody = json.get("messageBody");
    String writtenBy = json.get("writtenBy");

    return logMessageHandler.saveLogMessage(writtenBy, messageBody);
  }

}
