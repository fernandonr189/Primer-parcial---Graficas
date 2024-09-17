package models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class Pastel extends JPanel {

    private final BufferedImage bufferedImage;
    private final int width, height;
    private final Map<String, Integer> data;
    private int dataTotal = 0;
    private double currentAngle = 0;
    private final Point center;
    private final int radius = 250;
    private final Color[] colors = {Color.RED, Color.ORANGE, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.PINK};
    private int currentPosition = 0;
    private int positionDelta;

    public Pastel(BufferedImage _bufferedImage, int _width, int _height, Map<String, Integer> _data) {
        this.bufferedImage = _bufferedImage;
        this.width = _width;
        this.height = _height;
        this.data = _data;
        this.positionDelta = (width - 50) / data.size();
        setBackground(Color.BLACK);
        for(Map.Entry<String, Integer> entry: data.entrySet()) {
            dataTotal += entry.getValue();
        }
        center = new Point(
                width / 2,
                height / 2
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int index = 0;
        for(Map.Entry<String, Integer> entry: data.entrySet()) {
            //System.out.println(entry.getKey() + " " + entry.getValue());
            GradientPaint gradient = new GradientPaint(0, 0, colors[index], getWidth(), getHeight(), colors[index + 1]);
            paintSection(graphics2D, entry, gradient);
            paintIndicator(graphics2D, entry, gradient);
            index++;
        }
        g.drawImage(bufferedImage, 0, 0, this);
    }

    private void paintSection(Graphics2D graphics2D, Map.Entry<String, Integer> entry, GradientPaint gradient) {
        double angleDelta = (entry.getValue() * 2 * Math.PI) / dataTotal;
        graphics2D.setPaint(gradient);
        graphics2D.fillArc(
                center.x - radius,
                center.y - radius,
                radius * 2,
                radius * 2,
                radianToDegrees(currentAngle),
                radianToDegrees(angleDelta));
        currentAngle = currentAngle + angleDelta;
    }

    private void paintIndicator(Graphics2D graphics2D, Map.Entry<String, Integer> entry, GradientPaint gradient) {
        graphics2D.setPaint(gradient);
        graphics2D.fillRect(currentPosition + 5, height - 25, 20, 20);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(entry.getKey(), currentPosition + 30, height - 10);
        currentPosition += positionDelta;
    }

    private int radianToDegrees(double radians) {
        return (int) ceil(radians * 180 / Math.PI);
    }
}