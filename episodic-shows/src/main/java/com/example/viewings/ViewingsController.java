package com.example.viewings;

import com.example.shows.Episode;
import com.example.shows.EpisodeRepository;
import com.example.shows.Show;
import com.example.shows.ShowRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{id}")
public class ViewingsController {

  private final ViewingRepository repository;
  private final ShowRepository showRepository;
  private final EpisodeRepository episodeRepository;

  public ViewingsController(ViewingRepository repository,
                            ShowRepository showRepository,
                            EpisodeRepository episodeRepository) {
    this.repository = repository;
    this.showRepository = showRepository;
    this.episodeRepository = episodeRepository;
  }

  @GetMapping("/recently-watched")
  public List<ViewingWrapper> all(@PathVariable Long id) {
    Iterable<Viewing> viewings = repository.findAll();
    List<ViewingWrapper> wrappedViewings = new ArrayList<ViewingWrapper>();
    for (Viewing viewing : viewings) {
      Show show = showRepository.findOne(viewing.getShowId());
      Episode episode = episodeRepository.findOne(viewing.getEpisodeId());
      Date updatedAt = viewing.getUpdatedAt();
      int timecode = viewing.getTimecode();
      ViewingWrapper viewingWrapper = new ViewingWrapper(show, episode, updatedAt, timecode);
      wrappedViewings.add(viewingWrapper);
    }
    return wrappedViewings;
  }

  @PatchMapping("/viewings")
  public void createUpdate(@PathVariable Long id, @RequestBody Viewing updatedViewing) {
    // to-do: refactor to service
    Episode episode = episodeRepository.findOne(updatedViewing.getEpisodeId());
    Long showId = episode.getShowId();
    Viewing viewing = repository.findByUserIdAndShowId(id, showId);
    if (viewing == null) {
      updatedViewing.setUserId(id);
      updatedViewing.setShowId(showId);
      repository.save(updatedViewing);
    } else {
      viewing.setUserId(id);
      viewing.setShowId(showId);
      viewing.setEpisodeId(updatedViewing.getEpisodeId());
      viewing.setUpdatedAt(updatedViewing.getUpdatedAt());
      viewing.setTimecode(updatedViewing.getTimecode());
      repository.save(viewing);
    }
  }
}

//for final endpoint on shows api
//
// turn it into a hashmap, key off of the id
//  Map<Integer, Episode> episodeMap = new HashMap<>();
//  for (Episode e : episodes) {
//    episodeMap.put(e.id, e);
//  }
//
//  Map<Integer, Episode> episodeMap = episodes
//    .stream()
//    .collect(Collectors.toMap(e -> e.id, Function.identity()));
//
//  for (Viewing v : viewings) {
//    episodeMap.get(v.episodeId);
//  }