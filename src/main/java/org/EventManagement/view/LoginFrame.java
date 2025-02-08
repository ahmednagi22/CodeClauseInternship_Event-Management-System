package org.EventManagement.view;

import org.EventManagement.database.UserRepository;
import org.EventManagement.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginFrame extends JFrame{
    private JTextField userNameField;
    private JPasswordField passwordField;
    JButton loginButton;
    UserService userService = new UserService(new UserRepository());
    public LoginFrame() {
        setTitle("Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true); //remove header

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(130, 30, 150, 30);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 80, 100, 20);
        panel.add(userLabel);

        userNameField = new RoundedTextField(15);
        userNameField.setBounds(50, 100, 300, 40);
        panel.add(userNameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 150, 100, 20);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 170, 300, 40);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 230, 200, 40);
        loginButton.setBackground(new Color(51, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(new LoginButtonListener());

        panel.add(loginButton);

//        ImageIcon exitIcon = new ImageIcon("exitIcon.png");
//        JButton exitButton = new JButton();
//        exitButton.setIcon(exitIcon);
//        exitButton.setBounds(0,0,100,100);
//        panel.add(exitButton);
        add(panel);
    }
    private class LoginButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userNameField.getText();
            String password = new String(passwordField.getPassword());
            System.out.println("username : "+username + " password : "+password);
            if (userService.authenticateUser(username, password)){
                System.out.println("user exist ");
            }
            else{
                System.out.println("user not found");
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
