package org.EventManagement.database;

import org.EventManagement.models.Event;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public boolean addEvent(Event event) {
        String query = "INSERT INTO events (name, date, location, description) VALUES(?,?,?,?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, event.getName());
            statement.setDate(2, convertStringToSQLDate(event.getDate()));
            statement.setString(3, event.getLocation());
            statement.setString(4, event.getDescription());
            statement.executeUpdate();
            System.out.println("Event added successfully!");
            return true;
        } catch (SQLException e) {
                return false;
        }
    }

    public boolean updateEvent(Event event) {
        String query = "UPDATE events SET name = ?, date = ?, location = ?, description = ? WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, event.getName());
            statement.setDate(2, convertStringToSQLDate(event.getDate()));
            statement.setString(3, event.getLocation());
            statement.setString(4, event.getDescription());
            statement.setInt(5, event.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Event updated successfully!");
                return true;
            } else {
                System.out.println("No event found with that id.");
                return false;
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public Event getEventById(int id) {
        String query = "SELECT * FROM events WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEvent(resultSet);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving event: " + e.getMessage());
        }
    }

    public List<Event> getAllEvents() {
        String query = "SELECT * FROM events";
        List<Event> events = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                events.add(mapResultSetToEvent(resultSet));
            }
            System.out.println("Events retrieved successfully!");
            return events;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving events: " + e.getMessage());
        }
    }


    public boolean deleteEvent(int id) {
        String query = "DELETE FROM events WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Event deleted successfully!");
                return true;
            } else {
                System.out.println("No event found with that name.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error deleting event: " + e.getMessage());
        }
        return false;
    }

    private static Event mapResultSetToEvent(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getInt("id"));
        event.setName(resultSet.getString("name"));
        event.setDate(resultSet.getString("date"));
        event.setLocation(resultSet.getString("location"));
        event.setDescription(resultSet.getString("description"));
        return event;
    }

    public static Date convertStringToSQLDate(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr, DATE_FORMATTER);
        return Date.valueOf(localDate);
    }

}
