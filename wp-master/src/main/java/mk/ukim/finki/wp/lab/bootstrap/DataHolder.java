package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.EventBooking;
import mk.ukim.finki.wp.lab.model.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Event> events = new ArrayList<>(10);
    public static List<EventBooking> bookings = new ArrayList<>();
    public static List<Location> locations = new ArrayList<>();

    @PostConstruct
    public void init() {
        locations.add(new Location("Central Park", "123 Main St, New York, NY", "5000", "A large public park in New York City."));
        locations.add(new Location("Empire State Building", "20 W 34th St, New York, NY", "1000", "A famous skyscraper with stunning city views."));
        locations.add(new Location("Golden Gate Park", "San Francisco, CA", "10000", "A large urban park with gardens, museums, and cultural venues."));
        locations.add(new Location("Louvre Museum", "Rue de Rivoli, Paris, France", "2000", "The world's largest art museum, located in Paris."));
        locations.add(new Location("Sydney Opera House", "Bennelong Point, Sydney NSW, Australia", "1500", "A multi-venue performing arts center in Sydney."));

        events.add(new Event("Music Festival", "A three-day outdoor music festival featuring popular bands and solo artists.", 4.9));
        events.add(new Event("Art Exhibition", "An exhibition showcasing contemporary art from local and international artists.", 4.3));
        events.add(new Event("Food Truck Rally", "A gathering of gourmet food trucks offering a wide variety of cuisines.", 4.6));
        events.add(new Event("Marathon", "A city-wide marathon race attracting thousands of participants.", 4.5));
        events.add(new Event("Film Screening", "An exclusive screening of an award-winning documentary film.", 4.4));
        events.add(new Event("Science Fair", "An interactive fair displaying innovative projects from young scientists.", 4.8));
        events.add(new Event("Book Fair", "A week-long book fair with a variety of book genres and author signings.", 4.2));
        events.add(new Event("Wine Tasting", "A wine tasting event featuring local and international vineyards.", 4.7));
        events.add(new Event("Comedy Night", "A stand-up comedy show with performances by popular comedians.", 4.6));
        events.add(new Event("Charity Gala", "An annual charity gala to raise funds for local non-profits.", 4.9));
    }
}
