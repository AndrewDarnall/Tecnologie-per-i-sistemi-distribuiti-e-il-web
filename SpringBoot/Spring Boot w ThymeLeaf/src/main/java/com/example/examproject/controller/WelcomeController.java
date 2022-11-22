package com.example.examproject.controller;

import com.example.examproject.Event;
import com.example.examproject.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
public class WelcomeController {

    @Autowired
    private EventRepository eventRepository;
    @Value("Event Planner")
    String appName;


    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }



    @PostMapping(path="/") // Map ONLY POST Requests
    public String addNewEvent (Model model, @RequestParam String name, @RequestParam String location, @RequestParam String description, @RequestParam String date) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Event n = new Event();
        n.setName(name);
        n.setDate(date);
        n.setDescription(description);
        n.setLocation(location);
        eventRepository.save(n);
        Iterable<Event> allEvents = eventRepository.findAll();
        model.addAttribute("allEvents", allEvents);
        return "home";
    }

    @GetMapping(path = "/delete")
    public String deleteEvent(@RequestParam(value="id") int id) {
        //delete event by id
        System.out.println(id);
        return "home";

    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Event> getAllUsers() {
        // This returns a JSON or XML with the users
        return eventRepository.findAll();
    }
}
