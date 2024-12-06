package mk.ukim.finki.wp.lab.repository.inmemory;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.EventBooking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryEventBookingRepository {

    public void addBooking(EventBooking eventBooking) {
        DataHolder.bookings.add(eventBooking);
    }

    public List<EventBooking> listAllBookings() {
        return DataHolder.bookings;
    }

    public List<EventBooking> keywordSearch(String text) {
        return DataHolder.bookings.stream()
                .filter(booking -> booking.getEventName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }
}
