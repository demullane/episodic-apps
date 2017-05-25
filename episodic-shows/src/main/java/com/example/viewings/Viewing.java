package com.example.viewings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "viewings")
public class Viewing {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;
  private Long userId;
  private Long showId;
  @JsonIgnore
  private Long episodeId;
  private LocalDateTime updatedAt;
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

  @JsonProperty
  public void setEpisodeId(Long episodeId) { this.episodeId = episodeId; }

  public LocalDateTime getUpdatedAt() { return updatedAt; }

  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

  public int getTimecode() { return timecode; }

  public void setTimecode(int timecode) { this.timecode = timecode; }
}
