package org.EventManagement.view.manage;

import org.EventManagement.controller.ScheduleController;
import org.EventManagement.database.ScheduleRepository;
import org.EventManagement.models.Schedule;
import org.EventManagement.view.Dashboards.AdminDashboard;
import org.EventManagement.view.Authentication.LoginFrame;
import org.EventManagement.view.Dashboards.OrganizerDashboard;
import org.EventManagement.view.add.AddSchedule;
import org.EventManagement.view.edit.EditSchedule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ManageSchedules extends JFrame {
    private final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private final Color BUTTON_COLOR = new Color(52, 73, 94);
    private final Color BUTTON_HOVER_COLOR = new Color(67, 92, 115);
    private final Dimension SIDEBAR_SIZE = new Dimension(200, 0);
    private final ScheduleController scheduleController;
    DefaultTableModel tableModel;
    String title;
    public ManageSchedules(String title) {
        this.title =title;
        setTitle("Event Management System | Manage Schedules | " + title + " Panel");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        scheduleController =new ScheduleController(new ScheduleRepository());
        // Main Dashboard Panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Event ID","Activity", "Start Time", "End Time"};

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

        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(SIDEBAR_SIZE);

        // Sidebar Buttons
        String[] buttonLabels = {
                "Dashboard", "Add Schedule", "Edit Schedule",
                "Remove Schedule", "Logout"
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
            case "Dashboard" -> {
                switch (title) {
                    case "Admin" -> new AdminDashboard().setVisible(true);
                    case "Organizer" -> new OrganizerDashboard().setVisible(true);
                }
                this.dispose();

            }
            // Handle Dashboard button click
            case "Add Schedule" -> {
                AddSchedule addSchedule = new AddSchedule();
                addSchedule.setVisible(true);
                addSchedule.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        refreshTableData();
                    }
                });
            }
            case "Edit Schedule" -> {
                String  idStr = JOptionPane.showInputDialog(this,"Enter Schedule ID to Edit:");
                // validate input as Integer
                if(idStr != null && idStr.matches("\\d+")){
                    int scheduleId = Integer.parseInt(idStr);
                    Schedule schedule = scheduleController.getScheduleById(scheduleId);
                    if(schedule != null){
                        EditSchedule editSchedule = new EditSchedule(schedule);
                        editSchedule.setVisible(true);
                        editSchedule.addWindowListener(new java.awt.event.WindowAdapter() {
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
            case "Remove Schedule" -> {
                String  idStr = JOptionPane.showInputDialog(this,"Enter Schedule ID to Remove:");
                if(idStr != null && idStr.matches("\\d+")){
                    int scheduleId = Integer.parseInt(idStr);

                    if(scheduleController.deleteSchedule(scheduleId)){
                        JOptionPane.showMessageDialog(this, "Schedule Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTableData();

                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Schedule Not Found!!!", "Error", JOptionPane.ERROR_MESSAGE);
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
        List<Schedule> schedules = scheduleController.getAllSchedules();
        System.out.println("Fetched " + schedules.size() + " attendees"); // Debug statement
        // Repopulate the table
        for (Schedule schedule : schedules) {
            tableModel.addRow(
                    new Object[]{
                            schedule.getId(),
                            schedule.getEventId(),
                            schedule.getActivity(),
                            schedule.getStartTime(),
                            schedule.getEndTime()
                    }
            );
        }
    }
}