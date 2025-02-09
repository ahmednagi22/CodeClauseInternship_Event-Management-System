package org.EventManagement.view;

import org.EventManagement.database.UserRepository;
import org.EventManagement.models.User;
import org.EventManagement.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupFrame extends JFrame {
    private final JTextField userNameField;
    private final JPasswordField passwordField;
    private final JComboBox<String> roleComboBox;
    private final UserService userService;

    public SignupFrame() {
        setTitle("Sign Up");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true); // Remove header

        userService = new UserService(new UserRepository()); // service initialization

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(160, 20, 150, 30);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 50, 100, 20);
        panel.add(userLabel);

        userNameField = new JTextField(15);
        userNameField.setBounds(50, 70, 300, 40);
        userNameField.setBackground(Color.LIGHT_GRAY);
        userNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        userNameField.setBorder(null);
        panel.add(userNameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 120, 100, 20);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 140, 300, 40);
        passwordField.setBackground(Color.LIGHT_GRAY);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(null);
        panel.add(passwordField);

        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setBounds(50, 190, 100, 20);
        panel.add(roleLabel);

        String[] roles = {"organizer", "attendee"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(50, 210, 300, 40);
        roleComboBox.setBackground(Color.LIGHT_GRAY);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        roleComboBox.setBorder(null);
        roleComboBox.setSelectedIndex(-1); // Ensures no selection initially
        panel.add(roleComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 270, 200, 40);
        submitButton.setBackground(new Color(51, 153, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.addActionListener(new ButtonListener());

        panel.add(submitButton);
        add(panel);
    }
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userNameField.getText().trim();
            char[] passwordArray = passwordField.getPassword();
            String password = new String(passwordArray).trim();
            java.util.Arrays.fill(passwordArray, '\0'); // Clear password from memory
            String role = (roleComboBox.getSelectedItem() != null) ? roleComboBox.getSelectedItem().toString() : "";

            // Validate empty fields with detailed error messages
            if (username.isEmpty()) {
                showErrorDialog("Username is required!");
                return;
            }
            if (password.isEmpty()) {
                showErrorDialog("Password cannot be empty!");
                return;
            }
            if (password.length() < 6) {
                showErrorDialog("Password must be at least 6 characters!");
                return;
            }
            if (role.isEmpty()) {
                showErrorDialog("Please select a role!");
                return;
            }

            boolean flag = userService.addUser(new User(0, role, password, username));

            if (flag) {
                JOptionPane.showMessageDialog(
                        SignupFrame.this,
                        "You registered successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                new LoginFrame().setVisible(true);
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close registration frame
            } else {
                showErrorDialog("Username already taken!\nPlease try a different one.");
            }

            // Clear fields after registration
            userNameField.setText("");
            passwordField.setText("");
            roleComboBox.setSelectedIndex(-1);
        }

        private void showErrorDialog(String message) {
            JOptionPane.showMessageDialog(
                    SignupFrame.this,
                    message,
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
