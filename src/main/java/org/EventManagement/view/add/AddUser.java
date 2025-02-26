package org.EventManagement.view.add;

import org.EventManagement.database.UserRepository;
import org.EventManagement.models.User;
import org.EventManagement.controller.UserController;
import org.EventManagement.view.Utils.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUser extends JFrame {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final UserController userController;
    private JComboBox<String> roleComboBox;
    public AddUser() {
        userController = new UserController(new UserRepository()); // service initialization

        setTitle("Add User");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true); // Remove header

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Add User");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(140, 20, 150, 40);
        panel.add(titleLabel);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(Color.WHITE);
        firstNameLabel.setBounds(50, 70, 100, 20);
        panel.add(firstNameLabel);

        firstNameField = new JTextField(15);
        firstNameField.setBounds(50, 95, 140, 35);
        firstNameField.setBackground(Color.LIGHT_GRAY);
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        firstNameField.setBorder(null);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setBounds(210, 70, 100, 20);
        panel.add(lastNameLabel);

        lastNameField = new JTextField(15);
        lastNameField.setBounds(210, 95, 140, 35);
        lastNameField.setBackground(Color.LIGHT_GRAY);
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        lastNameField.setBorder(null);
        panel.add(lastNameField);



        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(50, 140, 100, 20);
        panel.add(emailLabel);

        emailField = new JTextField(15);
        emailField.setBounds(50, 165, 300, 35);
        emailField.setBackground(Color.LIGHT_GRAY);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(null);
        panel.add(emailField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 210, 100, 20);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 235, 300, 35);
        passwordField.setBackground(Color.LIGHT_GRAY);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(null);
        panel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setBounds(50, 280, 140, 20);
        panel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(50, 305, 300, 35);
        confirmPasswordField.setBackground(Color.LIGHT_GRAY);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmPasswordField.setBorder(null);
        panel.add(confirmPasswordField);

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setBounds(50, 345, 140, 20);
        panel.add(roleLabel);

        roleComboBox = new JComboBox<>(new String[]{"Admin", "Organizer", "Attendee"});
        roleComboBox.setSelectedIndex(-1);
        roleComboBox.setBounds(50, 370, 300, 35);
        roleComboBox.setBackground(Color.LIGHT_GRAY);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        roleComboBox.setBorder(null);
        add(roleComboBox);
        JButton exit = new JButton();
        exit.setBounds(367,5,30,30);
        ImageIcon icon = new ImageIcon("src/main/resources/Exit_button.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(36,36,Image.SCALE_SMOOTH));
        exit.setIcon(icon);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.addActionListener(e -> this.dispose());
        panel.add(exit);

        JButton AddUserButton = new JButton("Add User");
        AddUserButton.setBounds(100, 425, 200, 45);
        AddUserButton.setBackground(new Color(51, 153, 255));
        AddUserButton.setForeground(Color.WHITE);
        AddUserButton.setFont(new Font("Arial", Font.BOLD, 16));
        AddUserButton.setFocusPainted(false);
        AddUserButton.setBorderPainted(false);
        AddUserButton.addActionListener(new ButtonListener());

        panel.add(AddUserButton);
        add(panel);
    }
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String email = emailField.getText().trim();
                String userRole = (String) roleComboBox.getSelectedItem();
                char[] passwordArray = passwordField.getPassword();
                String password = new String(passwordArray).trim();
                java.util.Arrays.fill(passwordArray, '\0'); // Clear password from memory

                char[] confirmPasswordArray = confirmPasswordField.getPassword();
                String confirmPassword = new String(confirmPasswordArray).trim();
                java.util.Arrays.fill(confirmPasswordArray, '\0'); // Clear password from memory

                 System.out.println(userRole);

                // Validate empty fields with detailed error messages
                if (firstName.isEmpty()) {
                    showErrorDialog("First Name is Empty");
                    return;
                }
                if (lastName.isEmpty()) {
                    showErrorDialog("Last Name is Empty");
                    return;
                }
                if (email.isEmpty()) {
                    showErrorDialog("Email Is Required!");
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
                if (confirmPassword.isEmpty()) {
                    showErrorDialog("Please Confirm The Password");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    showErrorDialog("Passwords do not match!");
                    return;
                }
                if(userRole == null){
                showErrorDialog("Please Select a User Role");
                return;
                 }
                boolean flag = userController.addUser(new User(firstName, lastName, email, password, userRole));

                if (flag) {
                    JOptionPane.showMessageDialog(
                            AddUser.this,
                            "User Added Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close registration frame
                } else {
                    showErrorDialog("Email already taken!\nPlease try a different one.");
                }
            }
        }
        private void showErrorDialog(String message) {
            JOptionPane.showMessageDialog(
                    AddUser.this,
                    message,
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
