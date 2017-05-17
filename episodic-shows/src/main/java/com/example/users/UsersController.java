package com.example.users;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final UserRepository repository;

  //  command + n
  public UsersController(UserRepository repository) {
    this.repository = repository;
  }

  @PostMapping("")
  public User create(@RequestBody User user) {
    return this.repository.save(user);
  }

}
