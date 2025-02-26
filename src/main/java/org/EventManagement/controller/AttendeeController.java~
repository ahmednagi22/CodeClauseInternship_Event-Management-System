package org.EventManagement.controller;
import org.EventManagement.database.AttendeeRepository;
import org.EventManagement.models.Attendee;

import java.util.List;

public class AttendeeController {

    private final AttendeeRepository attendeeRepository;

    // dependency injection by constructor
    public AttendeeController(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }
    public boolean addAttendee(Attendee attendee){
        return attendeeRepository.addAttendee(attendee);
    }
    public boolean updateAttendee(Attendee attendee) {
        return attendeeRepository.updateAttendee(attendee);
    }

    public boolean deleteAttendee(int id) {
        return attendeeRepository.deleteAttendee(id);
    }
    public List<Attendee> getAllAttendees(){
        return attendeeRepository.getAllAttendees();
    }
    public Attendee getAttendeeById(int id) {
        return attendeeRepository.getAttendeeById(id);
    }
    public List<Attendee> getAttendeesByEvent(int eventId){
            return null;
    }

}
