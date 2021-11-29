package com.faketwitter.restserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.faketwitter.restserver.error.UserNotFoundException;
import com.faketwitter.restserver.handler.UserHandler;
import com.faketwitter.restserver.models.Message;
import com.faketwitter.restserver.models.User;
import com.faketwitter.restserver.rest.UserRESTController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRESTController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserHandler userHandler;

  @Autowired
  private ObjectMapper mapper;

  User mockUserOne = new User(0L, "ni4", "aaa", "johan", "nilsson", "ni4@kth.se", new ArrayList<>(), new ArrayList<>());
  User mockUserTwo = new User(1L, "ni5", "bbb", "johan", "nilsson", "ni5@kth.se", new ArrayList<>(), new ArrayList<>());

  Message mockMessageOne = new Message(1, "hej", mockUserOne);
  Message mockMessageTwo = new Message(2, "du", mockUserTwo);
  Message mockMessageThree = new Message(3, "hallo", mockUserTwo);
  Message mockMessageFour = new Message(4, "henlo", mockUserOne);

  List<User> userList = new ArrayList<>(Arrays.asList(
      mockUserOne,
      mockUserTwo
  ));

  List<Message> messageList = new ArrayList<>(Arrays.asList(
      mockMessageOne,
      mockMessageTwo,
      mockMessageThree,
      mockMessageFour
  ));

  @Before
  public void setUp() throws Exception {
    assertNotNull(mockUserOne);
    assertNotNull(mockUserTwo);
    mockUserOne.getLogMessages().add(mockMessageOne);
    mockUserOne.getPrivateMessages().add(mockMessageThree);
    mockUserTwo.getLogMessages().add(mockMessageTwo);
    mockUserTwo.getPrivateMessages().add(mockMessageFour);
  }

  @Test
  public void testRetrieveUserById() throws Exception {

    when(userHandler.getUserById(mockUserOne.getId())).thenReturn(Optional.ofNullable(mockUserOne));

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        .get("/api/users/{id}", mockUserOne.getId())
        .contentType("application/json"))
        .andExpect(status().isOk()).andReturn();

    System.out.println(result.getResponse().getContentAsString());

    mockMvc.perform(MockMvcRequestBuilders.
        get("/api/users/{id}", 5L)
        .contentType("application/json"))
        .andExpect(status().isNotFound());

  }

  @Test
  public void testRetrieveLogFromUser() throws Exception {

    when(userHandler.getUserLogMessagesByUsername(mockUserOne.getUsername())).thenReturn(mockUserOne.getLogMessages());
    when(userHandler.getUserLogMessagesById(mockUserOne.getId())).thenReturn(mockUserOne.getLogMessages());

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/users/log/name/{name}", mockUserOne.getUsername()))
        .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/users/log/name/{name}", "random"))
        .andExpect(content().string("[]"));

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/users/log/{id}", mockUserOne.getId()))
        .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/users/log/{id}", 5L))
        .andExpect(content().string("[]"));
  }

  @Test
  public void testRetrieveMessageFromUser() throws Exception {

    when(userHandler.getUserPrivateMessagesByUsername(mockUserOne.getUsername())).thenReturn(mockUserOne.getPrivateMessages());

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/users/messages/name/{name}", mockUserOne.getUsername()))
        .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/users/messages/name/{name}", "random"))
        .andExpect(content().string("[]"));

  }

  private class Credentials {
    private String username;
    private String password;

    public Credentials(String username, String password) {
      this.username = username;
      this.password = password;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  @Test
  public void testAuthentication() throws Exception {

    when(userHandler.authenticateUser(mockUserOne.getUsername(),mockUserOne.getPassword())).thenReturn(Optional.of(mockUserOne));
    mockMvc.perform(post("/api/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(new Credentials(mockUserOne.getUsername(), mockUserOne.getPassword()))))
        .andExpect(status().isOk());

    mockMvc.perform(post("/api/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(new Credentials(mockUserOne.getUsername(), ""))))
        .andExpect(status().isNotFound());

    mockMvc.perform(post("/api/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(new Credentials("", ""))))
        .andExpect(status().isNotFound());


  }

  static private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
