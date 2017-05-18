package com.example.shows;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "shows")
public class Show {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;

  public Show() {}

  public Long getId() { return id;}

  public String getName() { return name; }

  public void setName(String name) {
    this.name = name;
  }
}
