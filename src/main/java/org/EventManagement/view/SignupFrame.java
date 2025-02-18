package org.EventManagement.view;

import org.EventManagement.database.UserRepository;
import org.EventManagement.models.User;
import org.EventManagement.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupFrame extends JFrame {
    private final JTextField firstName;
    private final JTextField lastName;
    private final JTextField email;
    private final JPasswordField password;
    private final JPasswordField confirmPassword;
    private final UserController userController;

    public SignupFrame() {
        setTitle("Sign Up");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true); // Remove header

        userController = new UserController(new UserRepository()); // service initialization

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(140, 20, 150, 40);
        panel.add(titleLabel);



        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(Color.WHITE);
        firstNameLabel.setBounds(50, 70, 100, 20);
        panel.add(firstNameLabel);

        firstName = new JTextField(15);
        firstName.setBounds(50, 95, 140, 35);
        firstName.setBackground(Color.LIGHT_GRAY);
        firstName.setFont(new Font("Arial", Font.PLAIN, 16));
        firstName.setBorder(null);
        panel.add(firstName);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setBounds(210, 70, 100, 20);
        panel.add(lastNameLabel);

        lastName = new JTextField(15);
        lastName.setBounds(210, 95, 140, 35);
        lastName.setBackground(Color.LIGHT_GRAY);
        lastName.setFont(new Font("Arial", Font.PLAIN, 16));
        lastName.setBorder(null);
        panel.add(lastName);



        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(50, 140, 100, 20);
        panel.add(emailLabel);

        email = new JTextField(15);
        email.setBounds(50, 165, 300, 35);
        email.setBackground(Color.LIGHT_GRAY);
        email.setFont(new Font("Arial", Font.PLAIN, 16));
        email.setBorder(null);
        panel.add(email);
        
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 210, 100, 20);
        panel.add(passLabel);

        password = new JPasswordField();
        password.setBounds(50, 235, 300, 35);
        password.setBackground(Color.LIGHT_GRAY);
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setBorder(null);
        panel.add(password);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setBounds(50, 280, 140, 20);
        panel.add(confirmPasswordLabel);

        confirmPassword = new JPasswordField();
        confirmPassword.setBounds(50, 305, 300, 35);
        confirmPassword.setBackground(Color.LIGHT_GRAY);
        confirmPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmPassword.setBorder(null);
        panel.add(confirmPassword);

        JButton exit = new JButton();
        exit.setBounds(367,5,30,30);
        ImageIcon icon = new ImageIcon("G:\\colledge\\CodeClause Internship\\Event Management System\\src\\main\\java\\org\\EventManagement\\Exit_button.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(36,36,Image.SCALE_SMOOTH));
        exit.setIcon(icon);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.addActionListener(e -> System.exit(0));
        panel.add(exit);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 365, 200, 45);
        submitButton.setBackground(new Color(51, 153, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        //submitButton.addActionListener(new ButtonListener());

        panel.add(submitButton);
        add(panel);
    }
//    private class ButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String username = firstName.getText().trim();
//            char[] passwordArray = password.getPassword();
//            String password = new String(passwordArray).trim();
//            java.util.Arrays.fill(passwordArray, '\0'); // Clear password from memory
//            String role = (roleComboBox.getSelectedItem() != null) ? roleComboBox.getSelectedItem().toString() : "";
//
//            // Validate empty fields with detailed error messages
//            if (username.isEmpty()) {
//                showErrorDialog("Username is required!");
//                return;
//            }
//            if (password.isEmpty()) {
//                showErrorDialog("Password cannot be empty!");
//                return;
//            }
//            if (password.length() < 6) {
//                showErrorDialog("Password must be at least 6 characters!");
//                return;
//            }
//            if (role.isEmpty()) {
//                showErrorDialog("Please select a role!");
//                return;
//            }
//
//            boolean flag = userController.addUser(new User(0, role, password, username));
//
//            if (flag) {
//                JOptionPane.showMessageDialog(
//                        SignupFrame.this,
//                        "You registered successfully!",
//                        "Success",
//                        JOptionPane.INFORMATION_MESSAGE
//                );
//                new LoginFrame().setVisible(true);
//                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close registration frame
//            } else {
//                showErrorDialog("Username already taken!\nPlease try a different one.");
//            }
//
//            // Clear fields after registration
//            firstName.setText("");
//            password.setText("");
//            roleComboBox.setSelectedIndex(-1);
//        }
//
//        private void showErrorDialog(String message) {
//            JOptionPane.showMessageDialog(
//                    SignupFrame.this,
//                    message,
//                    "Registration Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }

}
