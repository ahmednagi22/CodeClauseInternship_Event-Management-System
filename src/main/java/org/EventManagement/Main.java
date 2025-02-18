package org.EventManagement;

import org.EventManagement.database.UserRepository;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
//        AttendeeRepository attendeeRepository = new AttendeeRepository();
//        AttendeeService attendeeService = new AttendeeService(attendeeRepository);
//        Attendee attendee = new Attendee(0,"Alice Johnson","alice@example.com","123-456-7890",2);
//       // attendeeService.addAttendee(attendee);
//          List<Attendee> attendees = attendeeService.getAllAttendees();
//        for (Attendee attendee1 : attendees) {
//            System.out.println("attendee: " + attendee1.getName() + ",\nemail: " + attendee1.getEmail()+
//                    ",\nphone: " + attendee1.getPhone());
//        }
        UserRepository u = new UserRepository();
        System.out.println(u.get_user_role("admin"));

    }
}