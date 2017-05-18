package com.example.shows;

import org.junit.Test;

public class ShowTest {

  @Test
  public void showTest() throws Exception {
    String name = "Show Name";

    Show show = new Show();
    show.setName(name);

    assert(show.getName().equals(name));
  }

}