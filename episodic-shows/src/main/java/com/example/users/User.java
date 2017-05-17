package com.example.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.util.Map;

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
