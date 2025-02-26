package org.EventManagement.view.add;

import com.toedter.calendar.JDateChooser;
import org.EventManagement.controller.EventController;
import org.EventManagement.database.EventRepository;
import org.EventManagement.models.Event;
import org.EventManagement.view.Utils.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEvent extends JFrame {
    private final JTextField nameField;
    private final JDateChooser date;
    private final JTextField location;
    private final JTextArea description;
    private final EventController eventController;
    private JComboBox<String> roleComboBox;

    public AddEvent() {
        eventController = new EventController(new EventRepository()); // service initialization

        setTitle("Add Event");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true); // Remove header

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Add Event");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(120, 20, 200, 40);
        panel.add(titleLabel);

        JLabel nameFieldLabel = new JLabel("Event Name");
        nameFieldLabel.setForeground(Color.WHITE);
        nameFieldLabel.setBounds(50, 70, 100, 20);
        panel.add(nameFieldLabel);

        nameField = new JTextField(15);
        nameField.setBounds(50, 95, 300, 30);
        nameField.setBackground(Color.LIGHT_GRAY);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBorder(null);
        panel.add(nameField);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setBounds(50, 135, 100, 20);
        panel.add(dateLabel);

        date = new JDateChooser();
        date.setBounds(50, 160, 300, 30);
        date.getDateEditor().getUiComponent().setBackground(Color.LIGHT_GRAY);
        date.setFont(new Font("Arial", Font.PLAIN, 16));
        date.getDateEditor().getUiComponent().setBorder(null);
        ((JTextField) date.getDateEditor().getUiComponent()).setEditable(false);
        date.setDateFormatString("yyyy-MM-dd"); // Set date format
        panel.add(date);

        JLabel locationLabel = new JLabel("Location");
        locationLabel.setForeground(Color.WHITE);
        locationLabel.setBounds(50, 200, 100, 20);
        panel.add(locationLabel);

        location = new JTextField(15);
        location.setBounds(50, 225, 300, 30);
        location.setBackground(Color.LIGHT_GRAY);
        location.setFont(new Font("Arial", Font.PLAIN, 16));
        location.setBorder(null);
        panel.add(location);

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setBounds(50, 265, 100, 20);
        panel.add(descriptionLabel);

        description = new JTextArea();
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBounds(50, 290, 300, 90);
        description.setBackground(Color.LIGHT_GRAY);
        description.setFont(new Font("Arial", Font.PLAIN, 16));
        description.setBorder(null);
        panel.add(description);

        JButton exit = new JButton();
        exit.setBounds(360, 5, 30, 30);
        ImageIcon icon = new ImageIcon("src/main/resources/Exit_button.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        exit.setIcon(icon);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.addActionListener(e -> this.dispose());
        panel.add(exit);

        JButton addEventButton = new JButton("Add Event");
        addEventButton.setBounds(100, 400, 200, 40);
        addEventButton.setBackground(new Color(51, 153, 255));
        addEventButton.setForeground(Color.WHITE);
        addEventButton.setFont(new Font("Arial", Font.BOLD, 16));
        addEventButton.setFocusPainted(false);
        addEventButton.setBorderPainted(false);
        addEventButton.addActionListener(new ButtonListener());

        panel.add(addEventButton);
        add(panel);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            String eventName = nameField.getText().trim();
            String eventDate = ((JTextField) date.getDateEditor().getUiComponent()).getText().trim();
            String eventLocation = location.getText().trim();
            String eventDescription = description.getText().trim();

            // Validate empty fields with detailed error messages
            if (eventName.isEmpty()) {
                showErrorDialog("Event Name is Empty");
                return;
            }
            if (eventDate.isEmpty()) {
                showErrorDialog("Date is Empty");
                return;
            }
            if (eventLocation.isEmpty()) {
                showErrorDialog("Location Not provided!");
                return;
            }
            if (eventDescription.isEmpty()) {
                showErrorDialog("Description should be added!");
                return;
            }

            boolean flag = eventController.addEvent(new Event(eventName, eventDate, eventLocation, eventDescription));

            if (flag) {
                JOptionPane.showMessageDialog(
                        AddEvent.this,
                        "Event Added Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose(); // Close registration frame
            } else {
                showErrorDialog("Can't add Event \n Please try again later");
            }
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                AddEvent.this,
                message,
                "Registration Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
