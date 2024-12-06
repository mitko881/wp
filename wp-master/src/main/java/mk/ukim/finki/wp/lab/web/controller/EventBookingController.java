package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab.model.EventBooking;
import mk.ukim.finki.wp.lab.service.EventBookingService;
import mk.ukim.finki.wp.lab.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/eventBooking")
public class EventBookingController {

    private final EventService eventService;
    private final EventBookingService eventBookingService;

    public EventBookingController(EventService eventService, EventBookingService eventBookingService) {
        this.eventService = eventService;
        this.eventBookingService = eventBookingService;
    }


    @GetMapping
    public String getEventBookingPage(@RequestParam(required = false) String attendeeName,
                                      @RequestParam(required = false) Long event,
                                      @RequestParam(required = false) Double numTickets,
                                      HttpServletRequest request,
                                      Model model) {

        HttpSession session = request.getSession();

        if (attendeeName != null) {
            session.setAttribute("attendeeName", attendeeName);
            String address = request.getRemoteAddr();
            String eventName = this.eventService.findById(event).get().getName();
            EventBooking booking = new EventBooking(eventName, attendeeName, address, numTickets);
            session.setAttribute("booking", booking);
        }

        model.addAttribute("booking", session.getAttribute("booking"));
        model.addAttribute("bookings", this.eventBookingService.listForAttendee(
                (String) session.getAttribute("attendeeName")));
        return "bookingConfirmation";
    }

    @PostMapping
    public String eventConfirmation(HttpServletRequest request) {

        HttpSession session = request.getSession();

        EventBooking booking = (EventBooking) session.getAttribute("booking");

        String attendeeName = (String) session.getAttribute("attendeeName");
        String address = booking.getAttendeeAddress();
        String eventName = booking.getEventName();
        Double numTickets = booking.getNumberOfTickets();

        this.eventBookingService.placeBooking(eventName, attendeeName, address, numTickets);

        return "redirect:/eventBooking";
    }
}
