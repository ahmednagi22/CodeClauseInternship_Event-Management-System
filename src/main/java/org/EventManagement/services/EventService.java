package org.EventManagement.services;
import org.EventManagement.database.EventRepository;
import org.EventManagement.models.Event;

import java.util.List;

public class EventService {

    private final EventRepository eventRepository;

    // dependency injection by constructor
    public EventService(EventRepository repository) {
        this.eventRepository = repository;
    }
    public void createEvent(Event event){
        eventRepository.createEvent(event);
    }
    public List<Event> getAllEvents(){
        return eventRepository.getAllEvents();
    }
    public void updateEvent(Event event) {
        eventRepository.updateEvent(event);
    }
    public void deleteEvent(int id) {
        eventRepository.deleteEvent(id);
    }
    public List<Event> searchEventsByName(String name) {
        return eventRepository.searchEventsByName(name);
    }
    public List<Event> searchEventsByDate(String date){
        return eventRepository.searchEventsByDate(date);
    }
}
