package com.faketwitter.restserver.repositories;

import com.faketwitter.restserver.models.User;
import com.faketwitter.restserver.repositories.projections.LogMessagesOnly;
import com.faketwitter.restserver.repositories.projections.PrivateMessagesOnly;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  LogMessagesOnly findLogMessagesById(long id);
  LogMessagesOnly findLogMessagesByUsername(String username);
  PrivateMessagesOnly findPrivateMessagesByUsername(String username);
  Optional<User> findUserByUsername(String username);

}
