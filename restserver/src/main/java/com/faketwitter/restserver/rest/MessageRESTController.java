package com.faketwitter.restserver.rest;

import com.faketwitter.restserver.handler.MessageHandler;
import com.faketwitter.restserver.handler.UserHandler;
import com.faketwitter.restserver.models.Message;
import com.faketwitter.restserver.models.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageRESTController {

  private final MessageHandler messageHandler;

  @Autowired
  public MessageRESTController(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  @GetMapping("")
  public List<Message> getAllMessages(){
    return messageHandler.getAllMessages();
  }

  @PostMapping("")
  public void saveMessage(@RequestBody Message message){
    messageHandler.saveMessage(message);
  }

}
