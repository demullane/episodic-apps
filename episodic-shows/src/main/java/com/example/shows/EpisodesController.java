package com.example.shows;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shows/{id}/episodes")
public class EpisodesController {

  private final EpisodeRepository repository;

  public EpisodesController(EpisodeRepository repository) { this.repository = repository; }

  @GetMapping("")
  public Iterable<Episode> all(@PathVariable Long id) {
    return repository.findByShowId(id);
  }

  @PostMapping("")
  public Episode create(@PathVariable Long id, @RequestBody Episode episode) {
    episode.setShowId(id);
    return repository.save(episode);
  }
}