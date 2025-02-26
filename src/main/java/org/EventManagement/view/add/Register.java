package org.EventManagement.view.add;

import org.EventManagement.controller.AttendeeController;
import org.EventManagement.controller.EventController;
import org.EventManagement.controller.UserController;
import org.EventManagement.database.AttendeeRepository;
import org.EventManagement.database.EventRepository;
import org.EventManagement.database.UserRepository;
import org.EventManagement.models.Attendee;
import org.EventManagement.models.Event;
import org.EventManagement.models.User;
import org.EventManagement.view.Utils.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
public class Register extends JFrame {
    private final JTextField nameField;
    private final JTextField emailField;
    private final JTextField phone;
    private final JComboBox<Integer> eventId;
    private final AttendeeController attendeeController;
    private final EventController eventController;
    private final UserController userController;
    public Register(String userEmail) {
        attendeeController = new AttendeeController(new AttendeeRepository()); // service initialization
        eventController = new EventController(new EventRepository());
        userController = new UserController(new UserRepository());
        User user = userController.getUserByEmail(userEmail);
        List<Event> events = eventController.getAllEvents();
        setTitle("Register");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true); // Remove header

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(140, 20, 200, 40);
        panel.add(titleLabel);

        JLabel nameFieldLabel = new JLabel("Name");
        nameFieldLabel.setForeground(Color.WHITE);
        nameFieldLabel.setBounds(50, 70, 100, 20);
        panel.add(nameFieldLabel);

        nameField = new JTextField(15);
        nameField.setBounds(50, 95, 300, 35);
        nameField.setBackground(Color.LIGHT_GRAY);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBorder(null);
        nameField.setText(user.getFirstName()+" "+user.getLastName());
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(50, 145, 100, 20);
        panel.add(emailLabel);

        emailField = new JTextField(15);
        emailField.setBounds(50, 170, 300, 35);
        emailField.setBackground(Color.LIGHT_GRAY);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(null);
        emailField.setText(user.getEmail());
        emailField.setEditable(false);
        panel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setBounds(50, 220, 100, 20);
        panel.add(phoneLabel);

        phone = new JTextField(15);
        phone.setBounds(50, 245, 300, 35);
        phone.setBackground(Color.LIGHT_GRAY);
        phone.setFont(new Font("Arial", Font.PLAIN, 16));
        phone.setBorder(null);
        panel.add(phone);

        JLabel eventIdLabel = new JLabel("Event ID");
        eventIdLabel.setForeground(Color.WHITE);
        eventIdLabel.setBounds(50, 295, 100, 20);
        panel.add(eventIdLabel);

        eventId = new JComboBox<>();
        for(Event event:events){
            eventId.addItem(event.getId());
        }
        eventId.setSelectedIndex(-1);
        eventId.setBounds(50, 320, 300, 35);
        eventId.setBackground(Color.LIGHT_GRAY);
        eventId.setFont(new Font("Arial", Font.PLAIN, 16));
        eventId.setBorder(null);
        panel.add(eventId);

        JButton exit = new JButton();
        exit.setBounds(360, 5, 30, 30);
        ImageIcon icon = new ImageIcon("src/main/resources/Exit_button.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        exit.setIcon(icon);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.addActionListener(e -> this.dispose());
        panel.add(exit);

        JButton addAttendeeButton = new JButton("Register");
        addAttendeeButton.setBounds(120, 400, 200, 45);
        addAttendeeButton.setBackground(new Color(51, 153, 255));
        addAttendeeButton.setForeground(Color.WHITE);
        addAttendeeButton.setFont(new Font("Arial", Font.BOLD, 16));
        addAttendeeButton.setFocusPainted(false);
        addAttendeeButton.setBorderPainted(false);
        addAttendeeButton.addActionListener(new ButtonListener());

        panel.add(addAttendeeButton);
        add(panel);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String attendeeName = nameField.getText().trim();
            String attendeeEmail = emailField.getText().trim();
            String attendeePhone = phone.getText().trim();
            Integer attendeeEventId = (Integer)eventId.getSelectedItem();

            if (attendeeName.isEmpty()) {
                showErrorDialog("Name is Empty");
                return;
            }
            if (attendeeEmail.isEmpty()) {
                showErrorDialog("Email is required");
                return;
            }
            if (attendeePhone.isEmpty()) {
                showErrorDialog("Phone is required!");
                return;
            }
            if (attendeeEventId == null) {
                showErrorDialog("Event ID is required!");
                return;
            }

            boolean flag = attendeeController.addAttendee(new Attendee(attendeeName, attendeeEmail, attendeePhone, attendeeEventId));

            if (flag) {
                JOptionPane.showMessageDialog(
                        Register.this,
                        "Attendee Added Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close registration frame
            } else {
                showErrorDialog("Can't add Attendee \nPlease try again later");
            }
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                Register.this,
                message,
                "Registration Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
