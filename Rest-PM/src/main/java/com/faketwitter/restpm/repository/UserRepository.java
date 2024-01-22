package com.faketwitter.restpm.repository;

import com.faketwitter.restpm.model.User;
import com.faketwitter.restpm.repository.projections.PrivateMessagesOnly;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findUserByUsername(String username);
    PrivateMessagesOnly findPrivateMessagesByUsername(String username);
}
