package org.EventManagement.view.manage;

import org.EventManagement.controller.EventController;
import org.EventManagement.database.EventRepository;
import org.EventManagement.models.Event;
import org.EventManagement.view.Dashboards.AdminDashboard;
import org.EventManagement.view.Authentication.LoginFrame;
import org.EventManagement.view.Utils.Utils;
import org.EventManagement.view.add.AddEvent;
import org.EventManagement.view.edit.EditEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManageEvents extends JFrame {
    private final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private final Color BUTTON_COLOR = new Color(52, 73, 94);
    private final Color BUTTON_HOVER_COLOR = new Color(67, 92, 115);
    private final Dimension SIDEBAR_SIZE = new Dimension(200, 0);
    private final EventController eventController;
    DefaultTableModel tableModel;
    private final JPanel cardsPanel;
    public ManageEvents(String title) {
        setTitle("Event Management System | Manage Events | "+title+" Panel");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        eventController =new EventController(new EventRepository());
        // Main Dashboard Panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Event Name","Date","Location", "Description"};

        tableModel = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        refreshTableData();

        cardsPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 Row, 3 Columns
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        updateCardsData(); // Initialize card panel with correct values
        // Add components to dashboardPanel
        dashboardPanel.add(tablePanel, BorderLayout.CENTER);
        dashboardPanel.add(cardsPanel, BorderLayout.SOUTH);

        // Add components to frame


        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(SIDEBAR_SIZE);

        // Sidebar Buttons
        String[] buttonLabels = {
                "Dashboard", "Add Event", "Edit Event",
                "Remove Event", "Logout"
        };

        for (String label : buttonLabels) {
            sidebar.add(createSidebarButton(label));
        }
        add(sidebar,BorderLayout.WEST);
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
        button.addActionListener(this::handleButtonClick);
        return button;
    }

    private void handleButtonClick(ActionEvent e) {
        String buttonText = e.getActionCommand();
        switch (buttonText) {
            case "Dashboard" -> {this.dispose();new AdminDashboard().setVisible(true);}
            // Handle Dashboard button click
            case "Add Event" -> {
                AddEvent addEvent = new AddEvent();
                addEvent.setVisible(true);
                addEvent.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        refreshTableData();
                        updateCardsData();
                    }
                });
            }
            case "Edit Event" -> {
                String  idStr = JOptionPane.showInputDialog(this,"Enter Event ID to Edit:");
                // validate input as Integer
                if(idStr != null && idStr.matches("\\d+")){
                    int eventId = Integer.parseInt(idStr);
                    Event event = eventController.getEventById(eventId);
                    if(event != null){
                        EditEvent editEvent = new EditEvent(event);
                        editEvent.setVisible(true);
                        editEvent.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent e) {
                                refreshTableData();
                                updateCardsData();
                            }
                        });
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Event not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Remove Event" -> {
                String  idStr = JOptionPane.showInputDialog(this,"Enter Event ID to Remove:");
                if(idStr != null && idStr.matches("\\d+")){
                    int eventId = Integer.parseInt(idStr);

                    if(eventController.deleteEvent(eventId)){
                        JOptionPane.showMessageDialog(this, "Event Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTableData();
                        updateCardsData();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Event Not Found!!!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Logout" -> {
                this.dispose();
                new LoginFrame().setVisible(true);
                JOptionPane.showMessageDialog(this, "You Logged Out");
            }
        }
    }
    private void refreshTableData() {
        // Clear existing rows
        tableModel.setRowCount(0);
        // Fetch updated list of users
        List<Event> events = eventController.getAllEvents();
        System.out.println("Fetched " + events.size() + " users"); // Debug statement
        // Repopulate the table
        for (Event event : events) {
            tableModel.addRow(
                    new Object[]{
                            event.getId(),
                            event.getName(),
                            event.getDate(),
                            event.getLocation(),
                            event.getDescription()
                    }
            );
        }
    }
    private void updateCardsData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        List<Event> events = eventController.getAllEvents();
        long totalEvents = events.size();

        long upcomingEvents = events.stream()
                .filter(event -> LocalDate.parse(event.getDate(), formatter).isAfter(today))
                .count();
        long pastEvents = events.stream()
                .filter(event -> LocalDate.parse(event.getDate(), formatter).isBefore(today)/* Add condition to check if event has passed */)
                .count();

        // Remove old components
        cardsPanel.removeAll();

        // Add updated event statistics cards
        cardsPanel.add(Utils.createCard(String.valueOf(totalEvents), "Total Events", new Color(243, 156, 18)));
        cardsPanel.add(Utils.createCard(String.valueOf(upcomingEvents), "Upcoming Events", new Color(52, 152, 219)));
        cardsPanel.add(Utils.createCard(String.valueOf(pastEvents), "Past Events", new Color(39, 174, 96)));

        // Refresh the UI
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

}