package com.example.shows;

import org.junit.Test;

import static org.junit.Assert.*;

public class EpisodeTest {

  @Test
  public void episodeTest() throws Exception {
    Long showId = new Long(1);
    Integer seasonNumber = 2;
    Integer episodeNumber = 3;

    Episode episode = new Episode();
    episode.setShowId(showId);
    episode.setSeasonNumber(seasonNumber);
    episode.setEpisodeNumber(episodeNumber);

    String title = "S" + seasonNumber.toString() + " E" + episodeNumber.toString();

    assert(episode.getShowId().equals(showId));
    assert(episode.getSeasonNumber().equals(seasonNumber));
    assert(episode.getEpisodeNumber().equals(episodeNumber));
    assert(episode.getTitle().equals(title));
  }



}