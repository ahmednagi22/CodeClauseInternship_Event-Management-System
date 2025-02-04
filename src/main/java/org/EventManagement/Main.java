package org.EventManagement;

import org.EventManagement.database.AttendeeRepository;
import org.EventManagement.database.DatabaseConnector;
import org.EventManagement.database.EventRepository;
import org.EventManagement.models.Attendee;
import org.EventManagement.models.Event;
import org.EventManagement.services.AttendeeService;
import org.EventManagement.services.EventService;

import java.sql.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        AttendeeRepository attendeeRepository = new AttendeeRepository();
        AttendeeService attendeeService = new AttendeeService(attendeeRepository);
        Attendee attendee = new Attendee(0,"Alice Johnson","alice@example.com","123-456-7890",2);
       // attendeeService.addAttendee(attendee);
          List<Attendee> attendees = attendeeService.getAllAttendees();
        for (Attendee attendee1 : attendees) {
            System.out.println("attendee: " + attendee1.getName() + ",\nemail: " + attendee1.getEmail()+
                    ",\nphone: " + attendee1.getPhone());
        }

    }
}