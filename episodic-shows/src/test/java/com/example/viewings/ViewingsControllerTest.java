package com.example.viewings;

import com.example.shows.Episode;
import com.example.shows.EpisodeRepository;
import com.example.shows.Show;
import com.example.shows.ShowRepository;
import com.example.users.User;
import com.example.users.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.toIntExact;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class ViewingsControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  UserRepository userRepository;

  @Autowired
  EpisodeRepository episodeRepository;

  @Autowired
  ShowRepository showRepository;

  @Autowired
  ViewingRepository viewingRepository;

  @Before
  public void setup() {
    userRepository.deleteAll();
    episodeRepository.deleteAll();
    showRepository.deleteAll();
    viewingRepository.deleteAll();
  }

  @Test
  public void testGet() throws Exception {
    String email = "email_test1@email.com";

    User user = new User();
    user.setEmail(email);
    userRepository.save(user);

    Long userId = user.getId();

    String email2 = "email_test2@email.com";

    User user2 = new User();
    user2.setEmail(email2);
    userRepository.save(user2);

    Long userId2 = user2.getId();

    String showName1 = "Show Name 1";

    Show show = new Show();
    show.setName(showName1);
    showRepository.save(show);

    Long showId = show.getId();
    Integer seasonNumber = 7;
    Integer episodeNumber = 28;

    Episode episode = new Episode();
    episode.setShowId(showId);
    episode.setSeasonNumber(seasonNumber);
    episode.setEpisodeNumber(episodeNumber);
    episodeRepository.save(episode);

    String showName2 = "Show Name 2";

    Show show2 = new Show();
    show2.setName(showName2);
    showRepository.save(show2);

    Long showId2 = show2.getId();
    Integer seasonNumber2 = 12;
    Integer episodeNumber2 = 15;

    Episode episode2 = new Episode();
    episode2.setShowId(showId2);
    episode2.setSeasonNumber(seasonNumber2);
    episode2.setEpisodeNumber(episodeNumber2);
    episodeRepository.save(episode2);

    Long episodeId = episode.getId();
    LocalDateTime updatedAt = LocalDateTime.of(2013,2,10,10,30,12);
    int timecode = 19;

    // first viewing for for user1
    Viewing viewing = new Viewing();
    viewing.setUserId(userId);
    viewing.setShowId(showId);
    viewing.setEpisodeId(episodeId);
    viewing.setUpdatedAt(updatedAt);
    viewing.setTimecode(timecode);
    viewingRepository.save(viewing);

    Long episodeId2 = episode2.getId();
    LocalDateTime updatedAt2 = LocalDateTime.of(2013,3,10,10,30,12);
    int timecode2 = 21;

    // second viewing for user1
    Viewing viewing2 = new Viewing();
    viewing2.setUserId(userId);
    viewing2.setShowId(showId2);
    viewing2.setEpisodeId(episodeId2);
    viewing2.setUpdatedAt(updatedAt2);
    viewing2.setTimecode(timecode2);
    viewingRepository.save(viewing2);

    // viewing for another user
    Viewing viewing3 = new Viewing();
    viewing3.setUserId(userId2);
    viewing3.setShowId(showId2);
    viewing3.setEpisodeId(episodeId2);
    viewing3.setUpdatedAt(updatedAt2);
    viewing3.setTimecode(timecode2);
    viewingRepository.save(viewing2);

    String episodeUrl = "/users/" + userId.toString() + "/recently-watched";
    MockHttpServletRequestBuilder request = get(episodeUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].show.id", equalTo(toIntExact(showId))))
        .andExpect(jsonPath("$[0].show.name", equalTo(showName1)))
        .andExpect(jsonPath("$[0].episode.id", equalTo(toIntExact(episodeId))))
        .andExpect(jsonPath("$[0].episode.seasonNumber", equalTo(seasonNumber)))
        .andExpect(jsonPath("$[0].episode.episodeNumber", equalTo(episodeNumber)))
        .andExpect(jsonPath("$[0].updatedAt", equalTo("2013-02-10T10:30:12")))
        .andExpect(jsonPath("$[0].timecode", equalTo(timecode)))
        .andExpect(jsonPath("$[1].show.id", equalTo(toIntExact(showId2))))
        .andExpect(jsonPath("$[1].show.name", equalTo(showName2)))
        .andExpect(jsonPath("$[1].episode.id", equalTo(toIntExact(episodeId2))))
        .andExpect(jsonPath("$[1].episode.seasonNumber", equalTo(seasonNumber2)))
        .andExpect(jsonPath("$[1].episode.episodeNumber", equalTo(episodeNumber2)))
        .andExpect(jsonPath("$[1].updatedAt", equalTo("2013-03-10T10:30:12")))
        .andExpect(jsonPath("$[1].timecode", equalTo(timecode2)));
  }

  @Test
  public void testCreate() throws Exception {
    Long count = viewingRepository.count();

    String email = "email_test1@email.com";

    User user = new User();
    user.setEmail(email);
    userRepository.save(user);

    Long userId = user.getId();

    String showName1 = "Show Name 1";

    Show show = new Show();
    show.setName(showName1);
    showRepository.save(show);

    Long showId = show.getId();
    Integer seasonNumber = 7;
    Integer episodeNumber = 28;

    Episode episode = new Episode();
    episode.setShowId(showId);
    episode.setSeasonNumber(seasonNumber);
    episode.setEpisodeNumber(episodeNumber);
    episodeRepository.save(episode);

    Long episodeId = episode.getId();
    LocalDateTime updatedAt = LocalDateTime.of(2014,2,10,10,30,12,9174500);;
    int timecode = 19;

    Map<String, Object> payload = new HashMap<String, Object>() {
      {
        put("episodeId", episodeId);
        put("updatedAt", updatedAt.toString());
        put("timecode", timecode);
      }
    };

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(payload);

    String episodeUrl = "/users/" + userId.toString() + "/viewings";
    MockHttpServletRequestBuilder request = patch(episodeUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(json);

    this.mvc.perform(request)
        .andExpect(status().isOk());

    assertThat(viewingRepository.count(), equalTo(count + 1));
  }

  @Test
  public void testUpdate() throws Exception {
    String email = "email_test1@email.com";

    User user = new User();
    user.setEmail(email);
    userRepository.save(user);

    Long userId = user.getId();

    String showName1 = "Show Name 1";

    Show show = new Show();
    show.setName(showName1);
    showRepository.save(show);

    Long showId = show.getId();
    Integer seasonNumber = 7;
    Integer episodeNumber = 28;

    Episode episode = new Episode();
    episode.setShowId(showId);
    episode.setSeasonNumber(seasonNumber);
    episode.setEpisodeNumber(episodeNumber);
    episodeRepository.save(episode);

    Long episodeId = episode.getId();
    LocalDateTime updatedAt = LocalDateTime.of(2015,2,10,10,30,12,9174500);;
    int timecode = 19;

    Viewing viewing = new Viewing();
    viewing.setUserId(userId);
    viewing.setShowId(showId);
    viewing.setEpisodeId(episodeId);
    viewing.setUpdatedAt(updatedAt);
    viewing.setTimecode(timecode);
    viewingRepository.save(viewing);

    Long count = viewingRepository.count();

    int newTimecode = 30;

    Map<String, Object> payload = new HashMap<String, Object>() {
      {
        put("episodeId", episodeId);
        put("updatedAt", updatedAt.toString());
        put("timecode", newTimecode);
      }
    };

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(payload);

    String episodeUrl = "/users/" + userId.toString() + "/viewings";
    MockHttpServletRequestBuilder request = patch(episodeUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(json);

    this.mvc.perform(request)
        .andExpect(status().isOk());

    assertThat(viewingRepository.count(), equalTo(count));
  }

}