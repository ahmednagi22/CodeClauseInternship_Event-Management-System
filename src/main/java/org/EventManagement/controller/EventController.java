package org.EventManagement.controller;
import org.EventManagement.database.EventRepository;
import org.EventManagement.models.Event;

import java.util.List;

public class EventController {

    private final EventRepository eventRepository;

    // dependency injection by constructor
    public EventController(EventRepository repository) {
        this.eventRepository = repository;
    }
    public boolean addEvent(Event event){
        return eventRepository.addEvent(event);
    }
    public boolean updateEvent(Event event) {
        return eventRepository.updateEvent(event);
    }
    public List<Event> getAllEvents(){
        return eventRepository.getAllEvents();
    }
    public boolean deleteEvent(int id) {
        return eventRepository.deleteEvent(id);
    }
    public Event getEventById(int id){
        return eventRepository.getEventById(id);
    }
}
