package org.EventManagement.view;

import org.EventManagement.controller.UserController;
import org.EventManagement.database.UserRepository;
import org.EventManagement.models.Event;
import org.EventManagement.models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ManageUsers extends JFrame {
    private static final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private static final Color BUTTON_COLOR = new Color(52, 73, 94);
    private static final Color BUTTON_HOVER_COLOR = new Color(67, 92, 115);
    private static final Dimension SIDEBAR_SIZE = new Dimension(200, 0);
    private static UserController userController;
    static DefaultTableModel tableModel;
    private static JPanel cardsPanel;
    public ManageUsers() {
        setTitle("Event Management System | Manage Users | Admin Panel");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        userController =new UserController(new UserRepository());
        // Main Dashboard Panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "First Name","Last Name","Email", "Role"};

        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        List<User> users = refreshTableData();

        cardsPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 Row, 3 Columns
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        updateCardsPanel(); // Initialize card panel with correct values
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
                "Dashboard", "Add User", "Edit User",
                "Remove User", "Logout"
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
        button.addActionListener(e -> handleButtonClick(e));
        return button;
    }

    private void handleButtonClick(ActionEvent e) {
        String buttonText = e.getActionCommand();
        switch (buttonText) {
            case "Dashboard" -> {this.dispose();new AdminDashboard().setVisible(true);}
            // Handle Dashboard button click
            case "Add User" -> {
                new AddUser().setVisible(true);
            }
            case "Edit User" -> {
                String  idStr = JOptionPane.showInputDialog(this,"Enter User ID to Edit:");
                // validate input as Integer
                if(idStr != null && idStr.matches("\\d+")){
                    int userId = Integer.parseInt(idStr);
                    User user = userController.getUserById(userId);
                    if(user != null){
                        new EditUser(user).setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Remove User" -> {
                String  idStr = JOptionPane.showInputDialog(this,"Enter User ID to Remove:");
                if(idStr != null && idStr.matches("\\d+")){
                    int userId = Integer.parseInt(idStr);

                    if(userController.deleteUser(userId)){
                        JOptionPane.showMessageDialog(this, "User Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTableData();
                        updateCardsPanel();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "User Not Found!!!", "Error", JOptionPane.ERROR_MESSAGE);
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
    public static List<User> refreshTableData() {
        // Clear existing rows
        tableModel.setRowCount(0);
        // Fetch updated list of users
        List<User> users = userController.getAllUsers();
        System.out.println("Fetched " + users.size() + " users"); // Debug statement
        // Repopulate the table
        for (User user : users) {
            tableModel.addRow(
                    new Object[]{
                            user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getRole()
                    }
            );
        }

        return users;
    }
    public static void updateCardsPanel() {
        long adminCount = userController.getAllUsers().stream().filter(user -> "Admin".equals(user.getRole())).count();
        long organizerCount = userController.getAllUsers().stream().filter(user -> "Organizer".equals(user.getRole())).count();
        long attendeeCount = userController.getAllUsers().stream().filter(user -> "Attendee".equals(user.getRole())).count();

        // Remove old components
        cardsPanel.removeAll();

        // Add updated cards
        cardsPanel.add(Utils.createCard(String.valueOf(adminCount), "Total Admins", new Color(243, 156, 18)));
        cardsPanel.add(Utils.createCard(String.valueOf(organizerCount), "Total Organizers", new Color(52, 152, 219)));
        cardsPanel.add(Utils.createCard(String.valueOf(attendeeCount), "Total Attendees", new Color(39, 174, 96)));

        // Refresh the UI
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManageUsers manageUsers = new ManageUsers();
            manageUsers.setVisible(true);
        });
    }

}