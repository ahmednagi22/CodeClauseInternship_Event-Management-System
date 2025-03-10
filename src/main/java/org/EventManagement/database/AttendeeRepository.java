package org.EventManagement.database;

import org.EventManagement.models.Attendee;
import org.EventManagement.models.Event;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendeeRepository {
    public boolean addAttendee(Attendee attendee) {
        String query = "INSERT INTO attendees (name, email, phone, event_id) VALUES(?,?,?,?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, attendee.getName());
            statement.setString(2, attendee.getEmail());
            statement.setString(3, attendee.getPhone());
            statement.setInt(4, attendee.getEventId());
            statement.executeUpdate();
            System.out.println("attendee added successfully!");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateAttendee(Attendee attendee) {
        String query = "UPDATE attendees SET name = ?, email = ?, phone = ? , event_id = ? WHERE id = ?";
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
                return true;
            } else {
                System.out.println("No attendee found with that id.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error updating attendee: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteAttendee(int id) {
        String query = "DELETE FROM attendees WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Attendee deleted successfully!");
                return true;
            } else {
                System.out.println("No attendee found with that id.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error deleting attendee: " + e.getMessage());
        }
        return false;
    }

    public List<Attendee> getAllAttendees() {
        String query = "SELECT * FROM attendees";
        List<Attendee> attendees = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                attendees.add(new Attendee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getInt("event_id")
                ));
            }
            System.out.println("Attendees retrieved successfully!");
            return attendees;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving attendees: " + e.getMessage());
        }
    }

    public Attendee getAttendeeById(int id) {
        String query = "SELECT * FROM attendees WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Attendee(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getInt("event_id")
                    );
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving attendee: " + e.getMessage());
        }
    }
    public List<Attendee> getAttendeesByEmail(String email) {
        String query = "SELECT * FROM attendees WHERE email = ?";
        List<Attendee> attendees = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    attendees.add( new Attendee(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getInt("event_id")
                    ));
                }
                return attendees;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving attendee: " + e.getMessage());
        }
    }
}

