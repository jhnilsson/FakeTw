package com.faketwitter.restpm.repository.projections;

import com.faketwitter.restpm.model.Message;
import java.util.List;

public interface PrivateMessagesOnly {
  List<Message> getPrivateMessages();
}
