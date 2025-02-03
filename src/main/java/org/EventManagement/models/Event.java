package org.EventManagement.models;
import java.time.LocalDate;
import java.sql.Date;

public class Event {
    private String name;
    private String date;
    private String location;
    private String description;

    public Event(String name, String date, String location, String description) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
    }

    public Event() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
