package org.EventManagement.database;

import org.EventManagement.models.Attendee;
import org.EventManagement.models.Event;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendeeRepository {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void addAttendee(Attendee attendee) {
        String query = "INSERT INTO attendees (name, email, phone, event_id) VALUES(?,?,?,?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, attendee.getName());
            statement.setString(2, attendee.getEmail());
            statement.setString(3, attendee.getPhone());
            statement.setInt(4, attendee.getEventId());
            statement.executeUpdate();
            System.out.println("attendee added successfully!");

        } catch (SQLException e) {
            throw new RuntimeException("Error adding attendee: " + e.getMessage());
        }
    }

    public void updateAttendee(Attendee attendee) {
        String query = "UPDATE attendee SET name = ?, email = ?, phone = ? , event_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, attendee.getName());
            statement.setString(2, attendee.getEmail());
            statement.setString(3, attendee.getPhone());
            statement.setInt(4, attendee.getEventId());
            statement.setInt(5, attendee.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Attendee updated successfully!");
            } else {
                System.out.println("No attendee found with that id.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating attendee: " + e.getMessage());
        }
    }

    public void deleteAttendee(int id) {
        String query = "DELETE FROM attendee WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Attendee deleted successfully!");
            } else {
                System.out.println("No attendee found with that id.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting attendee: " + e.getMessage());
        }
    }

    public List<Attendee> getAllAttendees() {
        String query = "SELECT * FROM attendees";
        List<Attendee> attendees = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                attendees.add(mapResultSetToAttendee(resultSet));
            }
            System.out.println("Attendees retrieved successfully!");
            return attendees;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving attendees: " + e.getMessage());
        }
    }

    public List<Attendee> searchAttendeeByName(String name) {
        String query = "SELECT * FROM attendees WHERE name = ?";
        List<Attendee> attendees = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    attendees.add(mapResultSetToAttendee(resultSet));
                }
            }
            System.out.println("Attendees retrieved successfully!");
            return attendees;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving attendees: " + e.getMessage());
        }
    }

    public List<Attendee> getAttendeesByEvent(int eventId) {
        String query = "SELECT * FROM attendees WHERE event_id = ?";
        List<Attendee> attendees = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, eventId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    attendees.add(mapResultSetToAttendee(resultSet));
                }
            }
            System.out.println("Attendees retrieved successfully!");
            return attendees;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving attendees: " + e.getMessage());
        }
    }

    private static Attendee mapResultSetToAttendee(ResultSet resultSet) throws SQLException {
        Attendee attendee = new Attendee();
        attendee.setId(resultSet.getInt("id"));
        attendee.setName(resultSet.getString("name"));
        attendee.setEmail(resultSet.getString("email"));
        attendee.setPhone(resultSet.getString("phone"));
        attendee.setEventId(resultSet.getInt("event_id"));
        return attendee;
    }

}

