package com.faketwitter.restlog.handlers;

import org.springframework.web.client.ResourceAccessException;

public class UserNotFoundException extends ResourceAccessException {

  public UserNotFoundException(String username) {
    super(username);
  }
}
