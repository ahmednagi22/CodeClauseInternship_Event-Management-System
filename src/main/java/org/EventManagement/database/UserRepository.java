package org.EventManagement.database;

import org.EventManagement.models.Attendee;
import org.EventManagement.models.Event;
import org.EventManagement.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public boolean addUser(User user) {
        String query = "INSERT INTO users (first_name, last_name, email, password, role) VALUES(?,?,?,?,?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.executeUpdate();
            System.out.println("user added successfully!");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateUser(User user) {
        String query = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, role = ? WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.setInt(6, user.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
                return true;
            } else {
                System.out.println("No user found with that id.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error updating User: " + e.getMessage());
            return false;
        }
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try(Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, email);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("role")
                    );
                }
                else {
                    System.out.println("User doesn't exist!!");
                    return null;
                }
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        try(Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, userId);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("role")
                    );
                }
                else {
                    System.out.println("User doesn't exist!!");
                    return null;
                }
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully!");
                return true;
            } else {
                System.out.println("No User found with that id.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error deleting User: " + e.getMessage());
            return false;
        }
    }

    public String get_user_role(String email){
        String query = "SELECT role FROM users WHERE email = ?";
        try(Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){

                System.out.println(resultSet.getString("role"));
                return resultSet.getString("role");
            }
            else {
                System.out.println("User doesn't exist!!");
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add( new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                ));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage());

        }
    }
//    public User searchUsersByUsername(String username) {
//        String query = "SELECT * FROM users WHERE username = ?";
//        User user = null;
//
//        try (Connection connection = DatabaseConnector.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, username);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    user = new User();
//                    user.setId(resultSet.getInt("id"));
//                    user.setUserName(resultSet.getString("username"));
//                    user.setPassword(resultSet.getString("password"));
//                    user.setRole(resultSet.getString("role"));
//                }
//            }
//            return user;
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving user: " + e.getMessage());
//
//        }
//
//    }

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
