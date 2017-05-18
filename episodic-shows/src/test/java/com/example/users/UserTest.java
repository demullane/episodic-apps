package com.example.users;

import org.junit.Test;

public class UserTest {

  @Test
  public void userTest() throws Exception {
    String email = "email@email.com";

    User user = new User();
    user.setEmail(email);

    assert(user.getEmail().equals(email));
  }

}