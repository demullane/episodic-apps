package com.example.viewings;

import com.example.shows.Episode;
import com.example.shows.Show;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class ViewingWrapper {
  private Show show;
  private Episode episode;
  private LocalDateTime updatedAt;
  private int timecode;

  public ViewingWrapper() {};

  public ViewingWrapper(Show show, Episode episode, LocalDateTime updatedAt, int timecode) {
    this.show = show;
    this.episode = episode;
    this.updatedAt = updatedAt;
    this.timecode = timecode;
  };

  public Show getShow() { return show; }

  public void setShow(Show show) { this.show = show; }

  public Episode getEpisode() { return episode; }

  public void setEpisode(Episode episode) { this.episode = episode; }

  public LocalDateTime getUpdatedAt() { return updatedAt; }

  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

  public int getTimecode() { return timecode; }

  public void setTimecode(int timecode) { this.timecode = timecode; }
}
