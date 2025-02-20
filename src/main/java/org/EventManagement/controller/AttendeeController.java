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
    public void addAttendee(Attendee attendee){
        attendeeRepository.addAttendee(attendee);
    }
    public void updateAttendee(Attendee attendee) {
        attendeeRepository.updateAttendee(attendee);
    }
    public void deleteAttendee(int id) {
        attendeeRepository.deleteAttendee(id);
    }
    public List<Attendee> getAllAttendees(){
        return attendeeRepository.getAllAttendees();
    }
    public List<Attendee> searchAttendeeByName(String name) {
        return attendeeRepository.searchAttendeeByName(name);
    }
    public List<Attendee> getAttendeesByEvent(int eventId){
            return null;
    }

}
