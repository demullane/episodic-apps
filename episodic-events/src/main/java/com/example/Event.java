package com.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Event.class, name = "event"),
    @JsonSubTypes.Type(value = Play.class, name = "play"),
    @JsonSubTypes.Type(value = Pause.class, name = "pause"),
    @JsonSubTypes.Type(value = FastForward.class, name = "fastForward"),
    @JsonSubTypes.Type(value = Rewind.class, name = "rewind"),
    @JsonSubTypes.Type(value = Progress.class, name = "progress"),
    @JsonSubTypes.Type(value = Scrub.class, name = "scrub"),

})
@JsonPropertyOrder({"type", "id", "userId", "showId", "episodeId", "createdAt", "data"})
public class Event {
  private String type;
  @Id
  private String id;
  private Long userId;
  private Long showId;
  private Long episodeId;
  private LocalDateTime createdAt;
  private Data data;

  public Event() {};

  public Event(Long userId, Long showId, Long episodeId, LocalDateTime createdAt) {
    this.userId = userId;
    this.showId = showId;
    this.episodeId = episodeId;
    this.createdAt = createdAt;
  }

  public String getType() { return type; }

  public String getId() { return id; }

  public Long getUserId() { return userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  public Long getShowId() { return showId; }

  public void setShowId(Long showId) { this.showId = showId; }

  public Long getEpisodeId() { return episodeId; }

  public void setEpisodeId(Long episodeId) { this.episodeId = episodeId; }

  public LocalDateTime getCreatedAt() { return createdAt; }

  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

  public Data getData() { return data; }

  public void setData(Data data) { this.data = data; }
}
