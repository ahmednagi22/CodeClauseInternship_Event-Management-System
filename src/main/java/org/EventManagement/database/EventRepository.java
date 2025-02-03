package org.EventManagement.database;

import org.EventManagement.models.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventRepository {



    public void addEvent(Event event){
        String query = "INSERT INTO events (name, date, location, description) VALUES(?,?,?,?)";
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,event.getName());
            statement.setDate(2,event.getDate());
            statement.setString(3,event.getLocation());
            statement.setString(4,event.getDescription());
            statement.execute();
            System.out.println("Event added successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
