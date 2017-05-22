package com.example.viewings;

import com.example.shows.Episode;
import com.example.shows.Show;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ViewingWrapper {
  private Show show;
  private Episode epsiode;
  private Date updatedAt;
  private int timecode;

  public ViewingWrapper() {};

  public ViewingWrapper(Show show, Episode episode, Date updatedAt, int timecode) {
    this.show = show;
    this.epsiode = episode;
    this.updatedAt = updatedAt;
    this.timecode = timecode;
  };

  public Show getShow() { return show; }

  public void setShow(Show show) { this.show = show; }

  public Episode getEpsiode() { return epsiode; }

  public void setEpsiode(Episode epsiode) { this.epsiode = epsiode; }

  public Date getUpdatedAt() { return updatedAt; }

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSS")
  public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

  public int getTimecode() { return timecode; }

  public void setTimecode(int timecode) { this.timecode = timecode; }
}

//"2017-05-04T11:45:34.9182"
