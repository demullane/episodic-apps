package com.example.shows;

import org.springframework.data.repository.CrudRepository;

public interface EpisodeRepository extends CrudRepository<Episode, Long> {
  Iterable<Episode> findByShowId(Long showId);
}
