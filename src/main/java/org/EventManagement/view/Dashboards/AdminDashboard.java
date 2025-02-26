package org.EventManagement.view.Dashboards;

import org.EventManagement.controller.AttendeeController;
import org.EventManagement.controller.EventController;
import org.EventManagement.controller.UserController;
import org.EventManagement.database.AttendeeRepository;
import org.EventManagement.database.EventRepository;
import org.EventManagement.database.UserRepository;
import org.EventManagement.models.Attendee;
import org.EventManagement.models.Event;
import org.EventManagement.models.User;
import org.EventManagement.view.Authentication.LoginFrame;
import org.EventManagement.view.Utils.Utils;
import org.EventManagement.view.manage.ManageAttendees;
import org.EventManagement.view.manage.ManageEvents;
import org.EventManagement.view.manage.ManageSchedules;
import org.EventManagement.view.manage.ManageUsers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminDashboard extends JFrame {
    private static final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private static final Color BUTTON_COLOR = new Color(52, 73, 94);
    private static final Color BUTTON_HOVER_COLOR = new Color(67, 92, 115);
    private static final Dimension SIDEBAR_SIZE = new Dimension(200, 0);

    public AdminDashboard() {
        EventController eventController = new EventController(new EventRepository());
        AttendeeController attendeeController = new AttendeeController(new AttendeeRepository());
        UserController userController = new UserController(new UserRepository());
        setTitle("Event Management System | Admin Panel");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(SIDEBAR_SIZE);

        // Sidebar Buttons
        String[] buttonLabels = {
                "Dashboard", "Manage Attendees", "Manage Events",
                "Manage Schedules", "Manage Users", "Logout"
        };

        for (String label : buttonLabels) {
            sidebar.add(createSidebarButton(label));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        java.util.List<Event> events = eventController.getAllEvents();
        java.util.List<Attendee> attendees = attendeeController.getAllAttendees();
        List<User> users = userController.getAllUsers();
        long totalEvents = events.size();

        long upcomingEvents = events.stream()
                .filter(event -> LocalDate.parse(event.getDate(), formatter).isAfter(today))
                .count();
        long pastEvents = events.stream()
                .filter(event -> LocalDate.parse(event.getDate(), formatter).isBefore(today)/* Add condition to check if event has passed */)
                .count();

        // Main Dashboard Panel
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        dashboardPanel.setBackground(Color.WHITE);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dashboardPanel.add(Utils.createCard(String.valueOf(totalEvents), "Total Events", new Color(52, 152, 219)));
        dashboardPanel.add(Utils.createCard(String.valueOf(upcomingEvents), "Upcoming Events", new Color(243, 156, 18)));
        dashboardPanel.add(Utils.createCard(String.valueOf(pastEvents), "Past Events", new Color(39, 174, 96)));
        dashboardPanel.add(Utils.createCard(String.valueOf(attendees.size()), "Total Attendees", new Color(46, 204, 113)));
        dashboardPanel.add(Utils.createCard(String.valueOf(users.size()), "Total Users", new Color(231, 76, 60)));

        add(sidebar, BorderLayout.WEST);
        add(dashboardPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 45));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }

        });
        button.addActionListener(e -> handleButtonClick(e));
        return button;
    }

    private void handleButtonClick(ActionEvent e) {
        String buttonText = e.getActionCommand();
        if (buttonText.equals("Dashboard")) {
            this.dispose();
            new AdminDashboard().setVisible(true);
        } else if (buttonText.equals("Manage Attendees")) {
            this.dispose();
            new ManageAttendees("Admin").setVisible(true);
        } else if (buttonText.equals("Manage Events")) {
            this.dispose();
            new ManageEvents("Admin").setVisible(true);
        } else if (buttonText.equals("Manage Schedules")) {
            this.dispose();
            new ManageSchedules("Admin").setVisible(true);
        } else if (buttonText.equals("Manage Users")) {
            this.dispose();
            new ManageUsers("Admin").setVisible(true);
        } else if (buttonText.equals("Logout")) {
            this.dispose();
            new LoginFrame().setVisible(true);
            JOptionPane.showMessageDialog(this,"You Logged Out");
        }
    }
}
