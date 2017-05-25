package com.example.viewings;

import com.example.shows.Episode;
import com.example.shows.EpisodeRepository;
import com.example.shows.Show;
import com.example.shows.ShowRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

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
    Iterable<Episode> episodes = episodeRepository.findAll();
    Map<Integer, Episode> episodeMap = new HashMap<>();
    for (Episode e : episodes) {
      episodeMap.put(toIntExact(e.getId()), e);
    }

    Iterable<Viewing> viewings = repository.findByUserId(id);
    List<ViewingWrapper> wrappedViewings = new ArrayList<ViewingWrapper>();
    for (Viewing v : viewings) {
      Episode episode = episodeMap.get(toIntExact(v.getEpisodeId()));
      Show show = showRepository.findOne(episode.getShowId());
      LocalDateTime updatedAt = v.getUpdatedAt();
      int timecode = v.getTimecode();
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