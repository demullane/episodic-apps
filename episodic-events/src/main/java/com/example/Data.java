package com.example;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {
  private Integer offset;
  private Integer startOffset;
  private Integer endOffset;
  private Float speed;

  public Data() {};

  public Integer getOffset() { return offset; }

  public void setOffset(Integer offset) { if (offset != null) this.offset = offset; }

  public Integer getStartOffset() { return startOffset; }

  public void setStartOffset(Integer startOffset) { if (startOffset != null) this.startOffset = startOffset; }

  public Integer getEndOffset() { return endOffset; }

  public void setEndOffset(Integer endOffset) { if (endOffset != null) this.endOffset = endOffset; }

  public Float getSpeed() { return speed; }

  public void setSpeed(Float speed) { if (speed != null) this.speed = speed; }
}
