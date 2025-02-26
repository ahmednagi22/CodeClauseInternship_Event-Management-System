package org.EventManagement.view.Utils;

import java.awt.*;
import javax.swing.*;

public class GradientPanel extends JPanel {
    private Color startColor = new Color(0, 150, 136); // Teal
    private Color endColor = new Color(63, 81, 181);  // Blue

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, startColor, width, height, endColor);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
    }
}
