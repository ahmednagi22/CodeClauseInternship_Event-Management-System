package org.EventManagement.view.manage;

import org.EventManagement.controller.AttendeeController;
import org.EventManagement.database.AttendeeRepository;
import org.EventManagement.models.Attendee;
import org.EventManagement.view.Dashboards.AdminDashboard;
import org.EventManagement.view.Authentication.LoginFrame;
import org.EventManagement.view.add.AddAttendee;
import org.EventManagement.view.edit.EditAttendee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ManageAttendees extends JFrame {
    private final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private final Color BUTTON_COLOR = new Color(52, 73, 94);
    private final Color BUTTON_HOVER_COLOR = new Color(67, 92, 115);
    private final Dimension SIDEBAR_SIZE = new Dimension(200, 0);
    private final AttendeeController attendeeController;
    DefaultTableModel tableModel;
    public ManageAttendees(String title) {
        setTitle("Event Management System | Manage Attendees | "+title+" Panel");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        attendeeController =new AttendeeController(new AttendeeRepository());
        // Main Dashboard Panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name","Email", "Event Id"};

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

        // Add components to dashboardPanel
        dashboardPanel.add(tablePanel, BorderLayout.CENTER);


        // Add components to frame


        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(SIDEBAR_SIZE);

        // Sidebar Buttons
        String[] buttonLabels = {
                "Dashboard", "Add Attendee", "Edit Attendee",
                "Remove Attendee", "Logout"
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
            case "Add Attendee" -> {
                AddAttendee addAttendee = new AddAttendee();
                addAttendee.setVisible(true);
                addAttendee.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        refreshTableData();

                    }
                });
            }
            case "Edit Attendee" -> {
                String  idStr = JOptionPane.showInputDialog(this,"Enter Attendee ID to Edit:");
                // validate input as Integer
                if(idStr != null && idStr.matches("\\d+")){
                    int attendeeId = Integer.parseInt(idStr);
                    Attendee attendee = attendeeController.getAttendeeById(attendeeId);
                    if(attendee != null){
                        EditAttendee editAttendee = new EditAttendee(attendee);
                        editAttendee.setVisible(true);
                        editAttendee.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent e) {
                                refreshTableData();
                            }
                        });
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Attendee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Remove Attendee" -> {
                String  idStr = JOptionPane.showInputDialog(this,"Enter Attendee ID to Remove:");
                if(idStr != null && idStr.matches("\\d+")){
                    int attendeeId = Integer.parseInt(idStr);

                    if(attendeeController.deleteAttendee(attendeeId)){
                        JOptionPane.showMessageDialog(this, "Attendee Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTableData();

                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Attendee Not Found!!!", "Error", JOptionPane.ERROR_MESSAGE);
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
        List<Attendee> attendees = attendeeController.getAllAttendees();
        System.out.println("Fetched " + attendees.size() + " attendees"); // Debug statement
        // Repopulate the table
        for (Attendee attendee : attendees) {
            tableModel.addRow(
                    new Object[]{
                            attendee.getId(),
                            attendee.getName(),
                            attendee.getEmail(),
                            attendee.getPhone(),
                            attendee.getEventId()
                    }
            );
        }
    }
}