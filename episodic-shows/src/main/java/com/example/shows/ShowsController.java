package com.example.shows;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shows")
public class ShowsController {

  private final ShowRepository repository;

  public ShowsController(ShowRepository repository) {
    this.repository = repository;
  }

  @GetMapping("")
  public Iterable<Show> all() {
    Iterable<Show> shows = this.repository.findAll();
    return shows;
  }

  @PostMapping("")
  public Show create(@RequestBody Show show) {
    return this.repository.save(show);
  }
}
