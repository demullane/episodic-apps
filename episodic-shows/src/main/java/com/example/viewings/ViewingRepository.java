package com.example.viewings;

import org.springframework.data.repository.CrudRepository;

public interface ViewingRepository extends CrudRepository<Viewing, Long> {
  Viewing findByUserIdAndShowId(Long userId, Long showId);
}
