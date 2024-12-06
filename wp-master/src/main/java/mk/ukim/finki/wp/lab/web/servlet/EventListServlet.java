package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab.service.EventService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(urlPatterns = "/list")
public class EventListServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final EventService eventService;

    public EventListServlet(EventService categoryService, SpringTemplateEngine springTemplateEngine) {
        this.eventService = categoryService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(request, response);

        WebContext context = new WebContext(webExchange);

        HttpSession session = request.getSession();
        Object filteredEvents = session.getAttribute("filteredEvents");

        if (filteredEvents == null) {
            session.setAttribute("events", this.eventService.listAll());
            context.setVariable("events", this.eventService.listAll());
        } else {
            context.setVariable("events", filteredEvents);
        }

        this.springTemplateEngine.process("listEvents.html", context, response.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String action = request.getParameter("action");

        if (action.equals("Filter")) {

            String minRatingStr = request.getParameter("minRating");
            Double minRating = minRatingStr != null && !minRatingStr.isEmpty() ? Double.parseDouble(minRatingStr) : 0.0;

            String filterText = request.getParameter("filterText");
            filterText = (filterText != null && !filterText.isEmpty()) ? filterText : " ";

            session.setAttribute("filteredEvents", this.eventService.searchEvents(filterText, minRating));
            response.sendRedirect("/list");

        } else if (action.equals("Reset")) {
            session.removeAttribute("filteredEvents");
            response.sendRedirect("/list");

        } else {
//          request.getRequestDispatcher("/eventBooking").forward(request, response);
            String attendeeName = request.getParameter("attendeeName");
            String address = request.getRemoteAddr();
            String eventName = request.getParameter("event");
            String numTickets = request.getParameter("numTickets");

            session.setAttribute("attendeeName", attendeeName);
            session.setAttribute("clientIpAddress", address);
            session.setAttribute("eventName", eventName);
            session.setAttribute("numTickets", numTickets);

            response.sendRedirect("/eventBooking");
        }
    }
}
