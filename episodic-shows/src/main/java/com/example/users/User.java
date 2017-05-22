package com.example.users;
import com.example.viewings.Viewing;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
