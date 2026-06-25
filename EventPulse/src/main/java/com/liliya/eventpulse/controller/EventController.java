package com.liliya.eventpulse.controller;

import com.liliya.eventpulse.entity.Event;
import com.liliya.eventpulse.service.EventService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "index";
    }

    @GetMapping("/searchEvents")
    public String searchEvents(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("events", eventService.searchEvents(keyword));
        model.addAttribute("keyword", keyword);
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