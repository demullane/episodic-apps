package com.example.users;

import javax.persistence.*;

@Entity(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String email;

  public User() {}

  public Long getId() { return id;}

  public String getEmail() { return email; }

  public void setEmail(String email) {
    this.email = email;
  }
}
