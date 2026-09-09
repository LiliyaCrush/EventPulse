package com.liliya.eventpulse.controller;

import com.liliya.eventpulse.entity.Event;
import com.liliya.eventpulse.service.EventService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        model.addAttribute("analytics", eventService.buildDashboardAnalytics(events));
        return "index";
    }

    @GetMapping("/searchEvents")
    public String searchEvents(@RequestParam("keyword") String keyword, Model model) {
        List<Event> allEvents = eventService.getAllEvents();
        model.addAttribute("events", eventService.searchEvents(keyword));
        model.addAttribute("keyword", keyword);
        // Portfolio analytics stay portfolio-wide while the table shows search hits.
        model.addAttribute("analytics", eventService.buildDashboardAnalytics(allEvents));
        return "index";
    }

    @GetMapping("/reports/attendance")
    public String attendanceReport(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("reportGeneratedAt", LocalDateTime.now());
        return "attendance_report";
    }

    @GetMapping("/showNewEventForm")
    public String showNewEventForm(Model model) {
        Event event = new Event();
        model.addAttribute("event", event);
        return "new_event";
    }

    @PostMapping("/saveEvent")
    public String saveEvent(@Valid @ModelAttribute("event") Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_event";
        }
        eventService.saveEvent(event);
        return "redirect:/";
    }

    @GetMapping("/deleteEvent/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "new_event";
    }
}
