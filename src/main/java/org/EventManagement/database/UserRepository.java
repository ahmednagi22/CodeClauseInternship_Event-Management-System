package org.EventManagement.database;

import org.EventManagement.models.Attendee;
import org.EventManagement.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public void addUser(User user) {
        String query = "INSERT INTO users (username, password, role) VALUES(?,?,?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
            System.out.println("user added successfully!");

        } catch (SQLException e) {
            throw new RuntimeException("Error adding user: " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setInt(4, user.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No User found with that id.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating User: " + e.getMessage());
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

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try(Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // return true if user exist

        }
        catch (Exception e) {
            return false;
        }
    }
}
