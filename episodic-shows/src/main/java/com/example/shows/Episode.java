package com.example.shows;

import com.example.viewings.Viewing;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "episodes")
public class Episode {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long showId;
  private Integer seasonNumber;
  private Integer episodeNumber;

  public Episode() {};

  public Long getId() { return id; }

  @JsonIgnore
  public Long getShowId() { return showId; }

  public void setShowId(Long showId) { this.showId = showId; }

  public Integer getSeasonNumber() { return seasonNumber; }

  public void setSeasonNumber(Integer seasonNumber) { this.seasonNumber = seasonNumber; }

  public Integer getEpisodeNumber() { return episodeNumber; }

  public void setEpisodeNumber(Integer episodeNumber) { this.episodeNumber = episodeNumber; }

  @Transient
  public String getTitle() {
    String title = "S" + seasonNumber.toString() + " E" + episodeNumber.toString();
    return title;
  }
}
