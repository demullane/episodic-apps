package com.example;

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

import java.time.LocalDateTime;
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
public class EventsControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  EventRepository eventRepository;

  @Before
  public void setup() { eventRepository.deleteAll(); }

  @Test
  public void testAll() throws Exception {
    // PLAY
    Long playUserId = new Long(1);
    Long playShowId = new Long(2);
    Long playEpisodeId = new Long(3);
    LocalDateTime playCreatedAt = LocalDateTime.of(2013,1,10,10,30,12,9174500);

    Data playData = new Data();
    Integer playOffset = new Integer(4);
    playData.setOffset(playOffset);

    Play play = new Play();
    play.setUserId(playUserId);
    play.setShowId(playShowId);
    play.setEpisodeId(playEpisodeId);
    play.setCreatedAt(playCreatedAt);
    play.setData(playData);
    eventRepository.save(play);

    // PAUSE
    Long pauseUserId = new Long(5);
    Long pauseShowId = new Long(6);
    Long pauseEpisodeId = new Long(7);
    LocalDateTime pauseCreatedAt = LocalDateTime.of(2013,2,10,10,30,12,9174500);

    Data pauseData = new Data();
    Integer pauseOffset = new Integer(8);
    pauseData.setOffset(pauseOffset);

    Pause pause = new Pause();
    pause.setUserId(pauseUserId);
    pause.setShowId(pauseShowId);
    pause.setEpisodeId(pauseEpisodeId);
    pause.setCreatedAt(pauseCreatedAt);
    pause.setData(pauseData);
    eventRepository.save(pause);

    // REWIND
    Long rewindUserId = new Long(9);
    Long rewindShowId = new Long(10);
    Long rewindEpisodeId = new Long(11);
    LocalDateTime rewindCreatedAt = LocalDateTime.of(2013,3,10,10,30,12,9174500);

    Data rewindData = new Data();
    Integer rewindStartOffset = new Integer(12);
    rewindData.setStartOffset(rewindStartOffset);
    Integer rewindEndOffset = new Integer(13);
    rewindData.setEndOffset(rewindEndOffset);
    Float rewindSpeed = new Float(13.1);
    rewindData.setSpeed(rewindSpeed);

    Rewind rewind = new Rewind();
    rewind.setUserId(rewindUserId);
    rewind.setShowId(rewindShowId);
    rewind.setEpisodeId(rewindEpisodeId);
    rewind.setCreatedAt(rewindCreatedAt);
    rewind.setData(rewindData);
    eventRepository.save(rewind);

    // FASTFORWARD
    Long fastForwardUserId = new Long(14);
    Long fastForwardShowId = new Long(15);
    Long fastForwardEpisodeId = new Long(16);
    LocalDateTime fastForwardCreatedAt = LocalDateTime.of(2013,4,10,10,30,12,9174500);

    Data fastForwardData = new Data();
    Integer fastForwardStartOffset = new Integer(17);
    fastForwardData.setStartOffset(rewindStartOffset);
    Integer fastForwardEndOffset = new Integer(18);
    fastForwardData.setEndOffset(fastForwardEndOffset);
    Float fastForwardSpeed = new Float(18.1);
    fastForwardData.setSpeed(fastForwardSpeed);

    FastForward fastForward = new FastForward();
    fastForward.setUserId(fastForwardUserId);
    fastForward.setShowId(fastForwardShowId);
    fastForward.setEpisodeId(fastForwardEpisodeId);
    fastForward.setCreatedAt(fastForwardCreatedAt);
    fastForward.setData(fastForwardData);
    eventRepository.save(fastForward);

    // PROGRESS
    Long progressUserId = new Long(19);
    Long progressShowId = new Long(20);
    Long progressEpisodeId = new Long(21);
    LocalDateTime progressCreatedAt = LocalDateTime.of(2013,5,10,10,30,12,9174500);

    Data progressData = new Data();
    Integer progressOffset = new Integer(22);
    progressData.setOffset(progressOffset);

    Progress progress = new Progress();
    progress.setUserId(progressUserId);
    progress.setShowId(progressShowId);
    progress.setEpisodeId(progressEpisodeId);
    progress.setCreatedAt(progressCreatedAt);
    progress.setData(progressData);
    eventRepository.save(progress);

    // SCRUB
    Long scrubUserId = new Long(23);
    Long scrubShowId = new Long(24);
    Long scrubEpisodeId = new Long(25);
    LocalDateTime scrubCreatedAt = LocalDateTime.of(2013,6,10,10,30,12,9174500);

    Data scrubData = new Data();
    Integer scrubStartOffset = new Integer(26);
    scrubData.setStartOffset(scrubStartOffset);
    Integer scrubEndOffset = new Integer(27);
    scrubData.setEndOffset(scrubEndOffset);

    Scrub scrub = new Scrub();
    scrub.setUserId(scrubUserId);
    scrub.setShowId(scrubShowId);
    scrub.setEpisodeId(scrubEpisodeId);
    scrub.setCreatedAt(scrubCreatedAt);
    scrub.setData(scrubData);
    eventRepository.save(scrub);

    MockHttpServletRequestBuilder request = get("/recent")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(6)))
        .andExpect(jsonPath("$[0].userId", equalTo(23)))
        .andExpect(jsonPath("$[1].userId", equalTo(19)))
        .andExpect(jsonPath("$[2].userId", equalTo(14)))
        .andExpect(jsonPath("$[3].userId", equalTo(9)))
        .andExpect(jsonPath("$[4].userId", equalTo(5)))
        .andExpect(jsonPath("$[5].userId", equalTo(1)));
  }

  @Test
  public void testEventCreate() throws Exception {
    Long count = eventRepository.count();

    MockHttpServletRequestBuilder request = post("/")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content("{\"type\":\"event\",\"userId\":52,\"showId\":987,\"episodeId\":456,\"createdAt\":\"2017-11-08T15:59:13.0091745\",\"data\":{\"offset\":0}}");

    this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", instanceOf(String.class)))
        .andExpect(jsonPath("$.type", equalTo("event")))
        .andExpect(jsonPath("$.userId", equalTo(52)))
        .andExpect(jsonPath("$.showId", equalTo(987)))
        .andExpect(jsonPath("$.episodeId", equalTo(456)))
        .andExpect(jsonPath("$.createdAt", equalTo("2017-11-08T15:59:13.0091745")))
        .andExpect(jsonPath("$.data.offset", equalTo(0)));

    assertThat(eventRepository.count(), equalTo(count + 1));
  }

}