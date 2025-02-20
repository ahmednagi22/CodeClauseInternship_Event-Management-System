package org.EventManagement.view;

import org.EventManagement.database.UserRepository;
import org.EventManagement.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame{
    private JTextField emailField;
    private JPasswordField passwordField;
    JButton loginButton, signupButton, exit;

    UserController userController = new UserController(new UserRepository());
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

        JLabel userLabel = new JLabel("Email");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 80, 100, 20);
        panel.add(userLabel);

        emailField = new JTextField(15);
        emailField.setBounds(50, 100, 300, 40);
        emailField.setBackground(Color.lightGray);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(null);
        panel.add(emailField);

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

        exit = new JButton();
        exit.setBounds(367,5,30,30);
        ImageIcon icon = new ImageIcon("G:\\colledge\\CodeClause Internship\\Event Management System\\src\\main\\java\\org\\EventManagement\\Exit_button.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(36,36,Image.SCALE_SMOOTH));
        exit.setIcon(icon);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.addActionListener(e -> System.exit(0));
        panel.add(exit);
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
                String username = emailField.getText();
                String password = new String(passwordField.getPassword());
                System.out.println("username : "+username + " password : "+password);
                if (userController.authenticateUser(username, password)){
                    //get user role
                    String role = userController.get_user_role(username);
                    switch (role) {
                        case "admin" -> new AdminDashboard().setVisible(true);
                        //case "organizer" -> new OrganizerDashboard().setVisible(true);
                        //case "attendee" -> new AttendeeDashboard().setVisible(true);
                    }
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
