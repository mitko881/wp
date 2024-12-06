package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // public List<Event> search(String text, Double rating) {
//        return DataHolder.events.stream()
//                .filter(event -> event.getName().contains(text) || event.getDescription().contains(text))
//                .filter(event -> event.getPopularityScore() > rating)
//                .collect(Collectors.toList());
//    }
    List<Event> findAllByNameLikeOrDescriptionLikeAndPopularityScoreGreaterThan(String name, String description, Double popularityScore);
}
