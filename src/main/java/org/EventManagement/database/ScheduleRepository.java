package org.EventManagement.database;

import org.EventManagement.models.Attendee;
import org.EventManagement.models.Event;
import org.EventManagement.models.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository {
    public boolean addSchedule(Schedule schedule) {
        String query = "INSERT INTO schedules (event_id, activity, start_time, end_time) VALUES(?,?,?,?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, schedule.getEventId());
            statement.setString(2, schedule.getActivity());
            statement.setTime(3, schedule.getStartTime());
            statement.setTime(4, schedule.getEndTime());
            statement.executeUpdate();
            System.out.println("Schedule added successfully!");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateSchedule(Schedule schedule) {
        String query = "UPDATE schedules SET event_id = ?, activity = ?, start_time = ?, end_time = ? WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, schedule.getEventId());
            statement.setString(2, schedule.getActivity());
            statement.setTime(3, schedule.getStartTime());
            statement.setTime(4, schedule.getEndTime());
            statement.setInt(5, schedule.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Schedule updated successfully!");
                return true;
            } else {
                System.out.println("No Schedule found with that id.");
                return false;
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteSchedule(int id) {
        String query = "DELETE FROM schedules WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Schedule deleted successfully!");
                return true;
            } else {
                System.out.println("No Schedule found with that Id.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error deleting Schedule: " + e.getMessage());
        }
        return false;
    }

    public Schedule getScheduleById(int id) {
        String query = "SELECT * FROM schedules WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Schedule(
                            resultSet.getInt("id"),
                            resultSet.getInt("event_id"),
                            resultSet.getString("activity"),
                            resultSet.getTime("start_time"),
                            resultSet.getTime("end_time")
                    );
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving event: " + e.getMessage());
        }
    }

    public List<Schedule> getAllSchedules() {
        String query = "SELECT * FROM schedules";
        List<Schedule> schedules = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                schedules.add(new Schedule(
                        resultSet.getInt("id"),
                        resultSet.getInt("event_id"),
                        resultSet.getString("activity"),
                        resultSet.getTime("start_time"),
                        resultSet.getTime("end_time")
                ));
            }
            System.out.println("Schedules retrieved successfully!");
            return schedules;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving schedules: " + e.getMessage());
        }
    }
}
