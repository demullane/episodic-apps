package com.example.viewings;

import com.example.shows.Episode;
import com.example.shows.EpisodeRepository;
import com.example.shows.Show;
import com.example.shows.ShowRepository;
import com.example.users.User;
import com.example.users.UserRepository;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.title;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
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
    Date updatedAt = new Date();
    int timecode = 19;

    Viewing viewing = new Viewing();
    viewing.setUserId(userId);
    viewing.setShowId(showId);
    viewing.setEpisodeId(episodeId);
    viewing.setUpdatedAt(updatedAt);
    viewing.setTimecode(timecode);

    String episodeUrl = "/users/" + userId.toString() + "/recently-watched";
    MockHttpServletRequestBuilder request = get(episodeUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    this.mvc.perform(request)
        .andExpect(status().isOk());
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
    Date updatedAt = new Date();
    int timecode = 19;

    Map<String, Object> payload = new HashMap<String, Object>() {
      {
        put("episodeId", episodeId);
        put("updatedAt", updatedAt);
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
    Date updatedAt = new Date();
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
        put("updatedAt", updatedAt);
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