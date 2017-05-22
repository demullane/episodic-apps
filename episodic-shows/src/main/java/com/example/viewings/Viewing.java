package com.example.viewings;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "viewings")
public class Viewing {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;
  private Long userId;
  private Long showId;
  private Long episodeId;
  private Date updatedAt;
  private int timecode;

  public Viewing() {};

  @JsonIgnore
  public Long getId() { return id; }

  @JsonIgnore
  public Long getUserId() { return userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  @JsonIgnore
  public Long getShowId() { return showId; }

  public void setShowId(Long showId) { this.showId = showId; }

  @JsonIgnore
  public Long getEpisodeId() { return episodeId; }

  public void setEpisodeId(Long episodeId) { this.episodeId = episodeId; }

//  @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
  public Date getUpdatedAt() { return updatedAt; }

  public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

  public int getTimecode() { return timecode; }

  public void setTimecode(int timecode) { this.timecode = timecode; }
}
