package org.EventManagement.view;

import org.EventManagement.controller.EventController;
import org.EventManagement.database.EventRepository;
import org.EventManagement.models.Event;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AttendeeDashboard extends JFrame {
    private static final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private static final Color BUTTON_COLOR = new Color(52, 73, 94);
    private static final Color BUTTON_HOVER_COLOR = new Color(67, 92, 115);
    private static final Dimension SIDEBAR_SIZE = new Dimension(200, 0);
    EventController eventController = new EventController(new EventRepository());
    public AttendeeDashboard() {
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
                "Dashboard",
                "Browse Events",
                "My Events",
                "Schedule",
                "Profile Settings",
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

        //data = eventController.getAllEvents();

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

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
        // Cards Panel (Using GridLayout to align properly)
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 Row, 3 Columns
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        cardsPanel.add(Utils.createCard(events.size()+"", "Total Events", new Color(52, 152, 219)));
        cardsPanel.add(Utils.createCard("2", "Upcoming Events", new Color(243, 156, 18)));
        cardsPanel.add(Utils.createCard("2", "Total Bookings", new Color(39, 174, 96)));

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
        if (buttonText.equals("Dashboard")) {
            new AttendeeDashboard().setVisible(true);
            dispose();
            // Handle Dashboard button click
        } else if (buttonText.equals("Manage Attendees")) {
            // Handle Manage Attendees button click
        } else if (buttonText.equals("Browse Events")) {
            // Handle Manage Events button click
        } else if (buttonText.equals("Schedule")) {
            // Handle Manage Schedule button click
        } else if (buttonText.equals("Profile Settings")) {
            // Handle Manage Users button click
        } else if (buttonText.equals("Logout")) {
            this.dispose();
            new LoginFrame().setVisible(true);
            JOptionPane.showMessageDialog(this,"You Logged Out");
        }
    }
    private void refreshTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clear existing data
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
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AttendeeDashboard dashboard = new AttendeeDashboard();
            dashboard.setVisible(true);
        });
    }
}
