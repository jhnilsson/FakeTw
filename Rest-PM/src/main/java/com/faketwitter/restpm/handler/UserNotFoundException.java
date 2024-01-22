package com.faketwitter.restpm.handler;

import org.springframework.web.client.ResourceAccessException;

public class UserNotFoundException extends ResourceAccessException {

  public UserNotFoundException(String s) {
    super(s);
  }
}
