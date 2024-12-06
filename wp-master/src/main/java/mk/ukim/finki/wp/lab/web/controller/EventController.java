package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.service.EventBookingService;
import mk.ukim.finki.wp.lab.service.EventService;
import mk.ukim.finki.wp.lab.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/events")
public class EventController {
    private final LocationService locationService;
    private final EventService eventService;
    private final EventBookingService eventBookingService;

    public EventController(EventService eventService, LocationService locationService,
                           EventBookingService eventBookingService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.eventBookingService = eventBookingService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("events", this.eventService.listAll());
        return "listEvents";
    }

    @GetMapping("/add")
    public String addEventPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("locations", this.locationService.findAll());
        return "add-event";
    }

    @PostMapping("/add")
    public String saveEvent(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam Double popularityScore,
                            @RequestParam Long location,
                            Model model) {

        this.eventService.saveEvent(name, description, popularityScore, location);

        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String editEventPage(@PathVariable Long id, @RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("event", this.eventService.findById(id).get());
        model.addAttribute("locations", this.locationService.findAll());
        return "edit-event";
    }

    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam Double popularityScore,
                            @RequestParam Long location,
                            Model model) {

        if (this.eventService.findById(id).isPresent()) {
            try {
                this.eventService.editEvent(id, name, description, popularityScore, location);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return "redirect:/events";
        }
        return "redirect:/events?error=ProductNotFound";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.eventService.removeById(id);
            return "redirect:/events";
    }

    @GetMapping("/details/{id}")
    public String getEventDetails(@PathVariable Long id, Model model) {
        Event event = this.eventService.findById(id).get();

        model.addAttribute("event", event);
        model.addAttribute("bookings", this.eventBookingService.search(event.getName()));

        return "details-event";
    }


    /// TODO Fix filter add filtering by location
}
