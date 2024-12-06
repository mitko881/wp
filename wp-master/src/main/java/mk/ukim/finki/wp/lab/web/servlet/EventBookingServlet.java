package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab.service.EventBookingService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;


@WebServlet(urlPatterns = "/servlet/eventBooking")
public class EventBookingServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private EventBookingService eventBookingService;

    public EventBookingServlet(SpringTemplateEngine springTemplateEngine, EventBookingService eventBookingService) {
        this.springTemplateEngine = springTemplateEngine;
        this.eventBookingService = eventBookingService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(request, response);

        WebContext context = new WebContext(webExchange);

        HttpSession session = request.getSession();

        context.setVariable("attendeeName", session.getAttribute("attendeeName"));
        context.setVariable("clientIpAddress", session.getAttribute("clientIpAddress"));
        context.setVariable("eventName", session.getAttribute("eventName"));
        context.setVariable("numTickets", session.getAttribute("numTickets"));


        String attendeeName = (String) session.getAttribute("attendeeName");
        String address = request.getRemoteAddr();
        String eventName = (String) session.getAttribute("eventName");
        String numTickets = (String) session.getAttribute("numTickets");

        this.eventBookingService.placeBooking(attendeeName, address, eventName, Double.parseDouble(numTickets));

        context.setVariable("bookings", eventBookingService.listForAttendee(attendeeName));

        this.springTemplateEngine.process("bookingConfirmation.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
//
//        IWebExchange webExchange = JakartaServletWebApplication
//                .buildApplication(getServletContext())
//                .buildExchange(request, response);
//
//        WebContext context = new WebContext(webExchange);

        String attendeeName = (String) session.getAttribute("attendeeName");
        String address = request.getRemoteAddr();
        String eventName = (String) session.getAttribute("eventName");
        String numTickets = (String) session.getAttribute("numTickets");

        this.eventBookingService.placeBooking(attendeeName, address, eventName, Double.parseDouble(numTickets));

//        context.setVariable("bookings", eventBookingService.listForAttendee(attendeeName));

        response.sendRedirect("/eventBooking");
    }
}
