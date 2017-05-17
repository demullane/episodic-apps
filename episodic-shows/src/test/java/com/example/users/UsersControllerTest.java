package com.example.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class UsersControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  UserRepository userRepository;

  @Before
  public void setup() {
    userRepository.deleteAll();
  }

  @Test
//  @Transactional
//  @Rollback
  public void testAll() throws Exception {
    String email1 = "email_test1@email.com";

    User user1 = new User();
    user1.setEmail(email1);
    userRepository.save(user1);

    String email2 = "email_test2@email.com";

    User user2 = new User();
    user2.setEmail(email2);
    userRepository.save(user2);

    MockHttpServletRequestBuilder request = get("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", equalTo(user1.getId().intValue())))
        .andExpect(jsonPath("$[0].email", equalTo(user1.getEmail())))
        .andExpect(jsonPath("$[1].id", equalTo(user2.getId().intValue())))
        .andExpect(jsonPath("$[1].email", equalTo(user2.getEmail())));
  }

  @Test
//  @Transactional
//  @Rollback
  public void testCreate() throws Exception {
    Long count = userRepository.count();
    String email = "joe@example.com";

    Map<String, Object> payload = new HashMap<String, Object>() {
      {
        put("email", email);
      }
    };

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(payload);

    MockHttpServletRequestBuilder request = post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(json);

    this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", instanceOf(Number.class)))
        .andExpect(jsonPath("$.email", equalTo(email)));

    assertThat(userRepository.count(), equalTo(count + 1));
  }
}

//    MvcResult result = this.mvc.perform(request).andReturn();
//    String content = result.getResponse().getContentAsString();
//    String blah = "blah";

