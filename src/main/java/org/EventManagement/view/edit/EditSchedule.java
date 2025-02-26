package org.EventManagement.view.edit;

import org.EventManagement.controller.EventController;
import org.EventManagement.controller.ScheduleController;
import org.EventManagement.database.EventRepository;
import org.EventManagement.database.ScheduleRepository;
import org.EventManagement.models.Event;
import org.EventManagement.models.Schedule;
import org.EventManagement.view.Utils.GradientPanel;
import org.EventManagement.view.add.AddSchedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditSchedule extends JFrame {
    private final JComboBox<Integer> eventIdCombo;
    private final JTextField activityField;
    private final JSpinner startTimeSpinner;
    private final JSpinner endTimeSpinner;
    private final ScheduleController scheduleController;
    private final EventController eventController;
    private Schedule schedule;
    public EditSchedule(Schedule schedule) {
        this.schedule = schedule;
        scheduleController = new ScheduleController(new ScheduleRepository());
        eventController = new EventController(new EventRepository());
        List<Event> events = eventController.getAllEvents();

        setTitle("Edit Schedule");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Edit Schedule");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(120, 20, 200, 40);
        panel.add(titleLabel);

        JLabel eventLabel = new JLabel("Event ID");
        eventLabel.setForeground(Color.WHITE);
        eventLabel.setBounds(50, 70, 100, 20);
        panel.add(eventLabel);

        eventIdCombo = new JComboBox<>();
        for(Event event:events){
            eventIdCombo.addItem(event.getId());
        }
        eventIdCombo.setSelectedItem(schedule.getEventId());
        eventIdCombo.setBounds(50, 95, 300, 35);
        eventIdCombo.setBackground(Color.LIGHT_GRAY);
        eventIdCombo.setBorder(null);
        eventIdCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(eventIdCombo);

        JLabel activityLabel = new JLabel("Activity");
        activityLabel.setForeground(Color.WHITE);
        activityLabel.setBounds(50, 145, 100, 20);
        panel.add(activityLabel);

        activityField = new JTextField(15);
        activityField.setBounds(50, 170, 300, 35);
        activityField.setBackground(Color.LIGHT_GRAY);
        activityField.setFont(new Font("Arial", Font.PLAIN, 16));
        activityField.setBorder(null);
        activityField.setText(schedule.getActivity());
        panel.add(activityField);

        JLabel startTimeLabel = new JLabel("Start Time (HH:mm)");
        startTimeLabel.setForeground(Color.WHITE);
        startTimeLabel.setBounds(50, 220, 150, 20);
        panel.add(startTimeLabel);

        startTimeSpinner = createTimeSpinner();
        startTimeSpinner.setBounds(50, 245, 300, 35);
        startTimeSpinner.setValue(schedule.getStartTime());
        panel.add(startTimeSpinner);

        JLabel endTimeLabel = new JLabel("End Time (HH:mm)");
        endTimeLabel.setForeground(Color.WHITE);
        endTimeLabel.setBounds(50, 295, 150, 20);
        panel.add(endTimeLabel);

        endTimeSpinner = createTimeSpinner();
        endTimeSpinner.setBounds(50, 320, 300, 35);
        endTimeSpinner.setValue(schedule.getEndTime());

        panel.add(endTimeSpinner);

        JButton addScheduleButton = new JButton("Edit Schedule");
        addScheduleButton.setBounds(100, 400, 200, 45);
        addScheduleButton.setBackground(new Color(51, 153, 255));
        addScheduleButton.setForeground(Color.WHITE);
        addScheduleButton.setFont(new Font("Arial", Font.BOLD, 16));
        addScheduleButton.setFocusPainted(false);
        addScheduleButton.setBorderPainted(false);
        addScheduleButton.addActionListener(new ButtonListener());
        panel.add(addScheduleButton);

        add(panel);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Integer selectedEventId = (Integer) eventIdCombo.getSelectedItem();
            String activity = activityField.getText().trim();
            Time startTime = getTimeFromSpinner(startTimeSpinner);
            Time endTime = getTimeFromSpinner(endTimeSpinner);

            if (selectedEventId == null) {
                showErrorDialog("Event ID is required!");
                return;
            }
            if (activity.isEmpty()) {
                showErrorDialog("Activity is required!");
                return;
            }
            schedule.setEventId(selectedEventId);
            schedule.setActivity(activity);
            schedule.setStartTime(startTime);
            schedule.setEndTime(endTime);

            boolean success = scheduleController.updateSchedule(schedule);

            if (success) {
                JOptionPane.showMessageDialog(EditSchedule.this, "Schedule Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showErrorDialog("Failed to Edit schedule!");
            }
        }
    }
    private JSpinner createTimeSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm");
        spinner.setEditor(editor);
        JFormattedTextField textField = editor.getTextField();
        textField.setEditable(false);
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setBorder(null);
        return spinner;
    }

    private Time getTimeFromSpinner(JSpinner spinner) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format((Date) spinner.getValue());
        return Time.valueOf(timeString + ":00"); // Convert string to SQL Time
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddSchedule dashboard = new AddSchedule();
            dashboard.setVisible(true);
        });
    }
}
