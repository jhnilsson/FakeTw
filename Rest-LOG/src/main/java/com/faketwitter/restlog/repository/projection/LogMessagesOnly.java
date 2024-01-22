package com.faketwitter.restlog.repository.projection;

import com.faketwitter.restlog.model.Message;
import java.util.List;

public interface LogMessagesOnly {
  List<Message> getLogMessages();
}
