package com.example.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
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

