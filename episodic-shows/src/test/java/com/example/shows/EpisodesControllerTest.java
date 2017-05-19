package com.example.shows;

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
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class EpisodesControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  EpisodeRepository episodeRepository;

  @Autowired
  ShowRepository showRepository;

  @Before
  public void setup() {
    showRepository.deleteAll();
    episodeRepository.deleteAll();
  }

  @Test
  public void testAll() throws Exception {
    String showName1 = "Show Name 1";

    Show show1 = new Show();
    show1.setName(showName1);
    showRepository.save(show1);

    Long showId1 = show1.getId();

    String showName2 = "Show Name 2";

    Show show2 = new Show();
    show2.setName(showName2);
    showRepository.save(show2);

    Long showId2 = show2.getId();

    Integer seasonNumber1 = 7;
    Integer episodeNumber1 = 28;

    Episode episode1 = new Episode();
    episode1.setShowId(showId1);
    episode1.setSeasonNumber(seasonNumber1);
    episode1.setEpisodeNumber(episodeNumber1);
    episodeRepository.save(episode1);

    Integer seasonNumber2 = 6;
    Integer episodeNumber2 = 20;

    Episode episode2 = new Episode();
    episode2.setShowId(showId1);
    episode2.setSeasonNumber(seasonNumber2);
    episode2.setEpisodeNumber(episodeNumber2);
    episodeRepository.save(episode2);

    Integer seasonNumber3 = 5;
    Integer episodeNumber3 = 18;

    Episode episode3 = new Episode();
    episode3.setShowId(showId2);
    episode3.setSeasonNumber(seasonNumber3);
    episode3.setEpisodeNumber(episodeNumber3);
    episodeRepository.save(episode3);

    String episodeUrl = "/shows/" + showId1.toString() + "/episodes";
    MockHttpServletRequestBuilder request = get(episodeUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", equalTo(episode1.getId().intValue())))
        .andExpect(jsonPath("$[0].seasonNumber", equalTo(episode1.getSeasonNumber())))
        .andExpect(jsonPath("$[0].episodeNumber", equalTo(episode1.getEpisodeNumber())))
        .andExpect(jsonPath("$[0].title", equalTo(episode1.getTitle())))
        .andExpect(jsonPath("$[0].showId").doesNotExist())
        .andExpect(jsonPath("$[1].seasonNumber", equalTo(episode2.getSeasonNumber())))
        .andExpect(jsonPath("$[1].episodeNumber", equalTo(episode2.getEpisodeNumber())))
        .andExpect(jsonPath("$[1].title", equalTo(episode2.getTitle())))
        .andExpect(jsonPath("$[1].showId").doesNotExist());
  }

  @Test
  public void testCreate() throws Exception {
    Long count = episodeRepository.count();

    String showName1 = "Show Name 1";

    Show show1 = new Show();
    show1.setName(showName1);
    showRepository.save(show1);

    Integer seasonNumber = 5;
    Integer episodeNumber = 28;

    Map<String, Object> payload = new HashMap<String, Object>() {
      {
        put("seasonNumber", seasonNumber);
        put("episodeNumber", episodeNumber);
      }
    };

    String title = "S" + seasonNumber.toString() + " E" + episodeNumber.toString();

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(payload);

    String episodeUrl = "/shows/" + show1.getId().toString() + "/episodes";
    MockHttpServletRequestBuilder request = post(episodeUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(json);

    this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", instanceOf(Number.class)))
        .andExpect(jsonPath("$.seasonNumber", equalTo(seasonNumber)))
        .andExpect(jsonPath("$.episodeNumber", equalTo(episodeNumber)))
        .andExpect(jsonPath("$.title", equalTo(title)));

    assertThat(episodeRepository.count(), equalTo(count + 1));
  }
}