package org.EventManagement.view.Dashboards;

import org.EventManagement.view.Authentication.LoginFrame;
import org.EventManagement.view.Utils.Utils;
import org.EventManagement.view.manage.ManageAttendees;
import org.EventManagement.view.manage.ManageEvents;
import org.EventManagement.view.manage.ManageSchedules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OrganizerDashboard extends JFrame {
    private static final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private static final Color BUTTON_COLOR = new Color(52, 73, 94);
    private static final Color BUTTON_HOVER_COLOR = new Color(67, 92, 115);
    private static final Dimension SIDEBAR_SIZE = new Dimension(200, 0);

    public OrganizerDashboard() {
        setTitle("Event Management System | Organizer Panel");
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
                "Manage Schedules", "Logout"
        };

        for (String label : buttonLabels) {
            sidebar.add(createSidebarButton(label));
        }

        // Main Dashboard Panel
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        dashboardPanel.setBackground(Color.WHITE);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dashboardPanel.add(Utils.createCard("7", "Total Events", new Color(52, 152, 219)));
        dashboardPanel.add(Utils.createCard("2", "Upcoming Events", new Color(243, 156, 18)));
        dashboardPanel.add(Utils.createCard("2", "Total Users", new Color(231, 76, 60)));
        dashboardPanel.add(Utils.createCard("2", "Total Bookings", new Color(243, 156, 18)));
        dashboardPanel.add(Utils.createCard("0", "New Schedule", new Color(52, 152, 219)));
        dashboardPanel.add(Utils.createCard("2", "Confirmed Bookings", new Color(46, 204, 113)));
        dashboardPanel.add(Utils.createCard("0", "Cancelled Bookings", new Color(231, 76, 60)));

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
        button.addActionListener(this::handleButtonClick);
        return button;
    }

    private void handleButtonClick(ActionEvent e) {
        String buttonText = e.getActionCommand();
        if (buttonText.equals("Dashboard")) {
            this.dispose();
            new OrganizerDashboard().setVisible(true);
        } else if (buttonText.equals("Manage Attendees")) {
            this.dispose();
            new ManageAttendees("Organizer").setVisible(true);
        } else if (buttonText.equals("Manage Events")) {
            this.dispose();
            new ManageEvents("Organizer").setVisible(true);
        } else if (buttonText.equals("Manage Schedules")) {
            this.dispose();
            new ManageSchedules("Organizer").setVisible(true);
        } else if (buttonText.equals("Logout")) {
            this.dispose();
            new LoginFrame().setVisible(true);
            JOptionPane.showMessageDialog(this,"You Logged Out");
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboard dashboard = new AdminDashboard();
            dashboard.setVisible(true);
        });
    }
}
