package com.example.shows;

import com.example.viewings.Viewing;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
