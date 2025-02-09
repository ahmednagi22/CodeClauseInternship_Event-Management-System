package org.EventManagement.view;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Event Management System | Admin Panel");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(14, 1, 5, 6));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(150, getHeight()));

        String[] menuItems = {
                "Dashboard", "Manage Attendees", "Manage Events", "Manage Schedules",
                "Manage Users","logout"
        };
        for (String item : menuItems) {
            JButton button = createNavButton(item);
            sidebar.add(button);
        }

        // Main Dashboard
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(2, 4, 15, 15));
        dashboardPanel.setBackground(Color.WHITE);

        dashboardPanel.add(createCard("7", "Listed Categories", new Color(52, 152, 219)));
        dashboardPanel.add(createCard("4", "Sponsors", new Color(46, 204, 113)));
        dashboardPanel.add(createCard("2", "Total Events", new Color(243, 156, 18)));
        dashboardPanel.add(createCard("2", "Total Reg. Users", new Color(231, 76, 60)));
        dashboardPanel.add(createCard("2", "Total Bookings", new Color(243, 156, 18)));
        dashboardPanel.add(createCard("0", "New Booking", new Color(52, 152, 219)));
        dashboardPanel.add(createCard("2", "Confirmed Booking", new Color(46, 204, 113)));
        dashboardPanel.add(createCard("0", "Cancelled Bookings", new Color(231, 76, 60)));

        add(sidebar, BorderLayout.WEST);
        //add(dashboardPanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94));
        button.setFocusPainted(false);
        return button;
    }

    private JPanel createCard(String number, String title, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 100));
        card.setBackground(color);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel numLabel = new JLabel(number, SwingConstants.CENTER);
        numLabel.setForeground(Color.WHITE);
        numLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JButton detailsButton = new JButton("View Details");
        detailsButton.setBackground(Color.WHITE);
        detailsButton.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(numLabel, BorderLayout.CENTER);
        card.add(detailsButton, BorderLayout.SOUTH);
        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });
    }
}
