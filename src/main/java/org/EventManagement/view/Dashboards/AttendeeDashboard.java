package org.EventManagement.view.Dashboards;

import org.EventManagement.controller.AttendeeController;
import org.EventManagement.controller.EventController;
import org.EventManagement.controller.ScheduleController;
import org.EventManagement.controller.UserController;
import org.EventManagement.database.AttendeeRepository;
import org.EventManagement.database.EventRepository;
import org.EventManagement.database.ScheduleRepository;
import org.EventManagement.database.UserRepository;
import org.EventManagement.models.Attendee;
import org.EventManagement.models.Event;
import org.EventManagement.models.Schedule;
import org.EventManagement.models.User;
import org.EventManagement.view.Authentication.LoginFrame;
import org.EventManagement.view.Utils.Utils;
import org.EventManagement.view.add.AddAttendee;
import org.EventManagement.view.add.Register;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class AttendeeDashboard extends JFrame {
    private static final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private static final Color BUTTON_COLOR = new Color(52, 73, 94);
    private static final Color BUTTON_HOVER_COLOR = new Color(67, 92, 115);
    private static final Dimension SIDEBAR_SIZE = new Dimension(200, 0);
    DefaultTableModel tableModel;
    JTable table;
    EventController eventController = new EventController(new EventRepository());
    AttendeeController attendeeController = new AttendeeController(new AttendeeRepository());
    ScheduleController scheduleController = new ScheduleController(new ScheduleRepository());
    List<Attendee> attendees;
    static String attendeeEmail ;
    public AttendeeDashboard(String email) {
        attendees = attendeeController.getAttendeesByEmail(email);
        UserController userController = new UserController(new UserRepository());
        System.out.println(attendees.size());
        this.attendeeEmail = email;
        setTitle("Event Management System | Attendee Panel");
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
                "View Events",
                "Register",
                "My Events",
                "View Schedules",
                "Edit Profile",
                "Cancel Registration",
                "Logout"
        };
        for (String label : buttonLabels) {
            sidebar.add(createSidebarButton(label));
        }

        // Main Dashboard Panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name","Date", "Location","Description"};

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        List<Event> events = eventController.getAllEvents();

        for (Event event : events) {
            tableModel.addRow(
                    new Object[]{
                            event.getId(),
                            event.getName(),
                            event.getDate(),
                            event.getLocation(),
                            event.getDescription()});
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        List<Attendee> attendees = attendeeController.getAllAttendees();
        List<User> users = userController.getAllUsers();
        long totalEvents = events.size();

        long upcomingEvents = events.stream()
                .filter(event -> LocalDate.parse(event.getDate(), formatter).isAfter(today))
                .count();
        long pastEvents = events.stream()
                .filter(event -> LocalDate.parse(event.getDate(), formatter).isBefore(today)/* Add condition to check if event has passed */)
                .count();

        // Cards Panel (Using GridLayout to align properly)
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 Row, 3 Columns
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        dashboardPanel.add(Utils.createCard(String.valueOf(totalEvents), "Total Events", new Color(52, 152, 219)));
        dashboardPanel.add(Utils.createCard(String.valueOf(upcomingEvents), "Upcoming Events", new Color(243, 156, 18)));
        dashboardPanel.add(Utils.createCard(String.valueOf(pastEvents), "Past Events", new Color(39, 174, 96)));
        dashboardPanel.add(Utils.createCard(String.valueOf(attendees.size()), "Total Attendees", new Color(46, 204, 113)));
        dashboardPanel.add(Utils.createCard(String.valueOf(users.size()), "Total Users", new Color(231, 76, 60)));

        // Add components to dashboardPanel
        dashboardPanel.add(tablePanel, BorderLayout.CENTER);
        dashboardPanel.add(cardsPanel, BorderLayout.SOUTH);

        // Add components to frame
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

        // Placeholder action listener (update this with actual navigation logic)
        button.addActionListener(e -> handleButtonClick(e));

        return button;
    }
    private void handleButtonClick(ActionEvent e) {
        String buttonText = e.getActionCommand();
        if (buttonText.equals("View Events")) {
            new AttendeeDashboard(attendeeEmail).setVisible(true);
            dispose();
        } else if (buttonText.equals("Register")) {
            new Register(attendeeEmail).setVisible(true);
        } else if (buttonText.equals("My Events")) {
            createRegisterEventsTable();
        } else if (buttonText.equals("View Schedules")) {
            createRegisterSchedulesTable();
        }
        else if (buttonText.equals("Logout")) {
            this.dispose();
            new LoginFrame().setVisible(true);
            JOptionPane.showMessageDialog(this,"You Logged Out");
        }
    }

    private void createRegisterEventsTable() {
        tableModel.setRowCount(0);

        for (Attendee attendee : attendees) {
            Event event = eventController.getEventById(attendee.getEventId());
            if (event != null) {
                tableModel.addRow(new Object[]{
                        event.getId(),
                        event.getName(),
                        event.getDate(),
                        event.getLocation(),
                        event.getDescription()
                });
            }
        }
    }

    private void createRegisterSchedulesTable() {
        String[] columns = {"ID", "Event Name","Activity", "Start Time","End Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        List<Schedule> schedules = scheduleController.getAllSchedules();
        for (Schedule schedule : schedules) {
            Event event = eventController.getEventById(schedule.getEventId());
            model.addRow(new Object[]{
                    schedule.getEventId(),
                    event.getName(),
                    schedule.getActivity(),
                    schedule.getStartTime(),
                    schedule.getEndTime()
            });
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        System.out.println("Schedulessss");
        JOptionPane.showMessageDialog(this, scrollPane, "Event Schedules", JOptionPane.PLAIN_MESSAGE);
    }

}
