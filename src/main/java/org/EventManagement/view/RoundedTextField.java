package org.EventManagement.view;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {
    private int arc = 25;

    public RoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(null);
        setFont(new Font("Arial", Font.PLAIN, 16));
        setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        super.paintComponent(g);
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {}
}
