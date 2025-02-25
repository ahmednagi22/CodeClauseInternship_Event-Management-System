package org.EventManagement.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public  class Utils {
    public static JPanel createCard(String number, String title, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setPreferredSize(new Dimension(180, 100));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel numLabel = new JLabel(number, SwingConstants.CENTER);
        numLabel.setForeground(Color.WHITE);
        numLabel.setFont(new Font("Arial", Font.BOLD, 28));

//        JButton detailsButton = new JButton("View Details");
//        detailsButton.setBackground(Color.WHITE);
//        detailsButton.setForeground(color);
//        detailsButton.setBorderPainted(false);
//        detailsButton.setFocusPainted(false);
//        detailsButton.setFont(new Font("Arial", Font.BOLD, 12));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(numLabel, BorderLayout.CENTER);
//        card.add(detailsButton, BorderLayout.SOUTH);
        return card;
    }


}
