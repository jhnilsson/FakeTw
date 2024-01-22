package com.faketwitter.restlog.repository;

import com.faketwitter.restlog.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface MessageRepository extends CrudRepository<Message, Long> {

}
