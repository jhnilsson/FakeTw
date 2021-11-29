package com.faketwitter.restserver.repositories.projections;

import com.faketwitter.restserver.models.Message;
import java.util.List;

public interface PrivateMessagesOnly {
  List<Message> getPrivateMessages();
}
