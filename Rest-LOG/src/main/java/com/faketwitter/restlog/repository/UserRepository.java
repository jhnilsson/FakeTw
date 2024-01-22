package com.faketwitter.restlog.repository;

import com.faketwitter.restlog.handlers.LogMessageHandler;
import com.faketwitter.restlog.model.User;
import com.faketwitter.restlog.repository.projection.LogMessagesOnly;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<User, Long> {
  LogMessagesOnly findLogMessagesByUsername(String username);
  LogMessagesOnly findLogMessagesById(long id);
  Optional<User> findUserByUsername(String username);
}
