package org.EventManagement.view;

import org.EventManagement.controller.AttendeeController;
import org.EventManagement.controller.EventController;
import org.EventManagement.database.AttendeeRepository;
import org.EventManagement.database.EventRepository;
import org.EventManagement.models.Attendee;
import org.EventManagement.models.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditAttendee extends JFrame {
    private final JTextField nameField;
    private final JTextField emailField;
    private final JTextField phone;
    private final JComboBox<Integer> eventId;
    private final AttendeeController attendeeController;
    private final EventController eventController;
    private final Attendee attendee;

    public EditAttendee(Attendee attendee) {
        this.attendee = attendee;
        attendeeController = new AttendeeController(new AttendeeRepository());
        eventController = new EventController(new EventRepository());
        List<Event> events = eventController.getAllEvents();

        setTitle("Edit Attendee");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Edit Attendee");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(120, 20, 200, 40);
        panel.add(titleLabel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(50, 70, 100, 20);
        panel.add(nameLabel);

        nameField = new JTextField(attendee.getName());
        nameField.setBounds(50, 95, 300, 35);
        nameField.setBackground(Color.LIGHT_GRAY);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBorder(null);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(50, 145, 100, 20);
        panel.add(emailLabel);

        emailField = new JTextField(attendee.getEmail());
        emailField.setBounds(50, 170, 300, 35);
        emailField.setBackground(Color.LIGHT_GRAY);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(null);
        panel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setBounds(50, 220, 100, 20);
        panel.add(phoneLabel);

        phone = new JTextField(attendee.getPhone());
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
        for (Event event : events) {
            eventId.addItem(event.getId());
        }
        eventId.setSelectedItem(attendee.getEventId());
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

        JButton updateAttendeeButton = new JButton("Update Attendee");
        updateAttendeeButton.setBounds(100, 400, 200, 45);
        updateAttendeeButton.setBackground(new Color(51, 153, 255));
        updateAttendeeButton.setForeground(Color.WHITE);
        updateAttendeeButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateAttendeeButton.setFocusPainted(false);
        updateAttendeeButton.setBorderPainted(false);
        updateAttendeeButton.addActionListener(new ButtonListener());

        panel.add(updateAttendeeButton);
        add(panel);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String attendeeName = nameField.getText().trim();
            String attendeeEmail = emailField.getText().trim();
            String attendeePhone = phone.getText().trim();
            Integer attendeeEventId = (Integer) eventId.getSelectedItem();

            if (attendeeName.isEmpty()) {
                showErrorDialog("Name is required");
                return;
            }
            if (attendeeEmail.isEmpty()) {
                showErrorDialog("Email is required");
                return;
            }
            if (attendeePhone.isEmpty()) {
                showErrorDialog("Phone is required");
                return;
            }
            if (attendeeEventId == null) {
                showErrorDialog("Event ID is required");
                return;
            }

            attendee.setName(attendeeName);
            attendee.setEmail(attendeeEmail);
            attendee.setPhone(attendeePhone);
            attendee.setEventId(attendeeEventId);

            boolean flag = attendeeController.updateAttendee(attendee);

            if (flag) {
                JOptionPane.showMessageDialog(
                        EditAttendee.this,
                        "Attendee Updated Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
            } else {
                showErrorDialog("Can't update Attendee. Please try again later.");
            }
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                EditAttendee.this,
                message,
                "Update Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
