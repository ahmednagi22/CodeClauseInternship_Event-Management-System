package org.EventManagement.models;

public class Attendee {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int eventId;

    public Attendee() {
    }

    public Attendee(int id, String name, String email, String phone,int eventId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.eventId = eventId;
    }

    public Attendee(String name, String email, String phone, int eventId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
