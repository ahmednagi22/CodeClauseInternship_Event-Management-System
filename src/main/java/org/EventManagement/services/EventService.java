package org.EventManagement.services;
import org.EventManagement.database.EventRepository;
import org.EventManagement.models.Event;

public class EventService {

    private final EventRepository repository;

    // dependency injection by constructor
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public void addEvent(Event event){
        repository.addEvent(event);
    }
}
