package com.example;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventsController {

  private final EventRepository repository;

  public EventsController(EventRepository repository) { this.repository = repository; }

  @GetMapping("/recent")
  public List<Event> getAll() {
    Pageable pageRequest = new PageRequest(0, 20, new Sort(Sort.Direction.DESC, "createdAt"));
    Page<Event> recentEvents = repository.findAll(pageRequest);
    return recentEvents.getContent();
  }

  @PostMapping("/")
  public Event createEvent(@RequestBody Event event) {
    return repository.save(event);
  }

}