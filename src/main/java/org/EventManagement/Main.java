package org.EventManagement;

import org.EventManagement.database.DatabaseConnector;
import org.EventManagement.database.EventRepository;
import org.EventManagement.models.Event;
import org.EventManagement.services.EventService;

import java.sql.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        EventRepository eventRepository = new EventRepository();
        EventService eventService = new EventService(eventRepository);
        //Event event = new Event("Tech Conference 2025","2025-06-16","New York","A technology conference for developers and startups.");
        //eventService.deleteEvent(event.getName());
       List<Event> events =  eventService.searchEventsByDate("2025-06-16");
        ///System.out.println(events);
        for (Event event : events) {
            System.out.println("Event: " + event.getName() + ",\nDate: " + event.getDate()+
                    ",\ndescription: " + event.getDescription());
        }

    }
}