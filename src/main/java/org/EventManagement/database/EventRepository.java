package org.EventManagement.database;

import org.EventManagement.models.Event;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {



    public void createEvent(Event event){
        String query = "INSERT INTO events (name, date, location, description) VALUES(?,?,?,?)";
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,event.getName());
            statement.setDate(2,convertStringToSQLDate(event.getDate()));
            statement.setString(3,event.getLocation());
            statement.setString(4,event.getDescription());
            statement.execute();
            System.out.println("Event added successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void updateEvent(Event event) {
        if(searchEventsByName(event.getName()).isEmpty()){
            System.out.println("No Event found with that name:");
        }
        else {
            String query = "UPDATE events SET date = ?, location = ?, description = ? WHERE name = ?";
            try {
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setDate(1,convertStringToSQLDate(event.getDate()));
                statement.setString(2,event.getLocation());
                statement.setString(3,event.getDescription());
                statement.setString(4,event.getName());
                statement.execute();
                System.out.println("Event updated successfully!");
            } catch (SQLException e) {
                System.out.println("Can't update event");
            }
        }
    }

    public List<Event> searchEventsByName(String name) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE name = ?";
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Event event = new Event();
                event.setName(resultSet.getString("name"));
                event.setDate(resultSet.getString("date"));
                event.setLocation(resultSet.getString("location"));
                event.setDescription(resultSet.getString("description"));
                events.add(event);
            }
            System.out.println("Events retrieved successfully!");
            return events;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving events: " + e.getMessage());
        }

    }

  public static Date convertStringToSQLDate(String dateStr) {
    LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    return Date.valueOf(localDate);
}

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events";
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Event event = new Event();
                event.setName(resultSet.getString("name"));
                event.setDate(resultSet.getString("date"));
                event.setLocation(resultSet.getString("location"));
                event.setDescription(resultSet.getString("description"));
                events.add(event);
            }
            System.out.println("Events retrieved successfully!");
            return events;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving events: " + e.getMessage());
        }
    }

    public List<Event> searchEventsByDate(String date) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE date = ?";
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,date);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Event event = new Event();
                event.setName(resultSet.getString("name"));
                event.setDate(resultSet.getString("date"));
                event.setLocation(resultSet.getString("location"));
                event.setDescription(resultSet.getString("description"));
                events.add(event);
            }
            System.out.println("Events retrieved successfully!");
            return events;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving events: " + e.getMessage());
        }
    }

    public void deleteEvent(String name) {
        String query = "DELETE FROM events WHERE name = ?";
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,name);
            statement.execute();
            System.out.println("Event deleted successfully!");

        } catch (SQLException e) {
            System.out.println("Can't delete event");
        }
    }
}
