package org.EventManagement.view;

import org.EventManagement.database.UserRepository;
import org.EventManagement.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame{
    private JTextField userNameField;
    private JPasswordField passwordField;
    JButton loginButton, signupButton;
    UserService userService = new UserService(new UserRepository());
    static LoginFrame loginFrame;
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

        userNameField = new JTextField(15);
        userNameField.setBounds(50, 100, 300, 40);
        userNameField.setBackground(Color.lightGray);
        userNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        userNameField.setBorder(null);
        panel.add(userNameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 150, 100, 20);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 170, 300, 40);
        passwordField.setBackground(Color.lightGray);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(null);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 230, 200, 40);
        loginButton.setBackground(new Color(51, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(new ButtonListener());

          panel.add(loginButton);
        signupButton = new JButton("Sign Up");
        signupButton.setBounds(125,280,150,30);
        signupButton.setBackground(new Color(80,153,255));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFont(new Font("Arial", Font.BOLD,16));
        signupButton.setFocusPainted(false);
        signupButton.setBorderPainted(false);
        signupButton.addActionListener(new ButtonListener());
        panel.add(signupButton);
        add(panel);
    }
    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == loginButton){
                String username = userNameField.getText();
                String password = new String(passwordField.getPassword());
                System.out.println("username : "+username + " password : "+password);
                if (userService.authenticateUser(username, password)){
                    new Dashboard().setVisible(true);
                    loginFrame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (e.getSource() == signupButton) {
                new SignupFrame().setVisible(true);
                loginFrame.dispose();
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
