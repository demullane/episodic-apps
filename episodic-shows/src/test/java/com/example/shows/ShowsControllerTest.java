package com.example.shows;

import com.example.shows.Show;
import com.example.shows.ShowRepository;
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
public class ShowsControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  ShowRepository showRepository;

  @Before
  public void setup() {
    showRepository.deleteAll();
  }

  @Test
  public void testAll() throws Exception {
    String name1 = "Show Name 1";

    Show show1 = new Show();
    show1.setName(name1);
    showRepository.save(show1);

    String name2 = "Show Name 2";

    Show show2 = new Show();
    show2.setName(name2);
    showRepository.save(show2);

    MockHttpServletRequestBuilder request = get("/shows")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", equalTo(show1.getId().intValue())))
        .andExpect(jsonPath("$[0].name", equalTo(show1.getName())))
        .andExpect(jsonPath("$[1].id", equalTo(show2.getId().intValue())))
        .andExpect(jsonPath("$[1].name", equalTo(show2.getName())));
  }

  @Test
  public void testCreate() throws Exception {
    Long count = showRepository.count();
    String name = "Show Name";

    Map<String, Object> payload = new HashMap<String, Object>() {
      {
        put("name", name);
      }
    };

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(payload);

    MockHttpServletRequestBuilder request = post("/shows")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(json);

    this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", instanceOf(Number.class)))
        .andExpect(jsonPath("$.name", equalTo(name)));

    assertThat(showRepository.count(), equalTo(count + 1));
  }
}