package com.faketwitter.restpm.repository;

import com.faketwitter.restpm.model.Message;
import com.faketwitter.restpm.repository.projections.PrivateMessagesOnly;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface MessageRepository extends CrudRepository<Message, Long> {
}
