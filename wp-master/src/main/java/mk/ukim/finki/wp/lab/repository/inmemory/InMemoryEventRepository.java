package mk.ukim.finki.wp.lab.repository.inmemory;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Event;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryEventRepository {

    public List<Event> findAll() {
        return DataHolder.events;
    }

    public List<Event> search(String text, Double rating) {
        return DataHolder.events.stream()
                .filter(event -> event.getName().contains(text) || event.getDescription().contains(text))
                .filter(event -> event.getPopularityScore() > rating)
                .collect(Collectors.toList());
    }

    public Boolean removeById(Long id) {
        return DataHolder.events.removeIf(event -> event.getId().equals(id));
    }

    public Boolean saveEvent(String name, String description, Double popularityScore, Long locationId) {
        return DataHolder.events.add(new Event(name, description, popularityScore));
    }

    public Optional<Event> findById(Long id) {
        return DataHolder.events.stream().filter(event -> event.getId().equals(id)).findFirst();
    }

    public Event editEvent(Long id, String name, String description, Double popularityScore, Long locationId) {
        Event event = findById(id).get();
        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setLocation(DataHolder.locations.stream()
                .filter(location -> location.getId().equals(locationId))
                .findFirst().get());
        return event;
    }
}
