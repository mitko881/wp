package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.Location;
import mk.ukim.finki.wp.lab.repository.jpa.EventRepository;
import mk.ukim.finki.wp.lab.repository.jpa.LocationRepository;
import mk.ukim.finki.wp.lab.service.EventService;
import mk.ukim.finki.wp.lab.service.LocationService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    @PostConstruct
    public void init() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Central Park", "123 Main St, New York, NY", "5000", "A large public park in New York City."));
        locations.add(new Location("Empire State Building", "20 W 34th St, New York, NY", "1000", "A famous skyscraper with stunning city views."));
        locations.add(new Location("Golden Gate Park", "San Francisco, CA", "10000", "A large urban park with gardens, museums, and cultural venues."));
        locations.add(new Location("Louvre Museum", "Rue de Rivoli, Paris, France", "2000", "The world's largest art museum, located in Paris."));
        locations.add(new Location("Sydney Opera House", "Bennelong Point, Sydney NSW, Australia", "1500", "A multi-venue performing arts center in Sydney."));

        this.locationRepository.saveAll(locations);

        List<Location> savedLocations = this.locationRepository.findAll();
        int size = savedLocations.size();
        int c = size;

        List<Event> events = new ArrayList<>();
        events.add(new Event("Music Festival", "A three-day outdoor music festival featuring popular bands and solo artists.", 4.9, savedLocations.get((c++) % size)));
        events.add(new Event("Art Exhibition", "An exhibition showcasing contemporary art from local and international artists.", 4.3, savedLocations.get((c++) % size)));
        events.add(new Event("Food Truck Rally", "A gathering of gourmet food trucks offering a wide variety of cuisines.", 4.6, savedLocations.get((c++) % size)));
        events.add(new Event("Marathon", "A city-wide marathon race attracting thousands of participants.", 4.5, savedLocations.get((c++) % size)));
        events.add(new Event("Film Screening", "An exclusive screening of an award-winning documentary film.", 4.4, savedLocations.get((c++) % size)));
        events.add(new Event("Science Fair", "An interactive fair displaying innovative projects from young scientists.", 4.8, savedLocations.get((c++) % size)));
        events.add(new Event("Book Fair", "A week-long book fair with a variety of book genres and author signings.", 4.2, savedLocations.get((c++) % size)));
        events.add(new Event("Wine Tasting", "A wine tasting event featuring local and international vineyards.", 4.7, savedLocations.get((c++) % size)));
        events.add(new Event("Comedy Night", "A stand-up comedy show with performances by popular comedians.", 4.6, savedLocations.get((c++) % size)));
        events.add(new Event("Charity Gala", "An annual charity gala to raise funds for local non-profits.", 4.9, savedLocations.get((c++) % size)));

        eventRepository.saveAll(events);
    }
}
