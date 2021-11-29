package com.faketwitter.restserver.repositories;

import com.faketwitter.restserver.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
