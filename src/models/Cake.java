package models;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.lang.Math.ceil;

public class Cake extends JPanel {
    private BufferedImage backgroundImage;
    private final BufferedImage bufferedImage;
    private final int width, height;
    private Map<String, Integer> data;
    private int dataTotal = 0;
    private double currentAngle = 0;
    private final Point center;
    private final int radius = 250;
    private final Color[] colors = {Color.RED, Color.ORANGE, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.PINK};
    private int currentPosition = 0;
    private final int positionDelta;
    private boolean isUpdating = true;

    public Cake(BufferedImage _bufferedImage, int _width, int _height, Map<String, Integer> _data) {
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
    public void update(Graphics g) {
        super.update(g);
        isUpdating = true;
        paintComponent(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        if(isUpdating) {
            drawBackgroundImage(graphics2D);
            isUpdating = false;
        }
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int index = 0;
        for(Map.Entry<String, Integer> entry: data.entrySet()) {
            if(index >= colors.length - 1) {
                index = 0;
            }
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

    private void drawBackgroundImage(Graphics2D g) {
        try {
            backgroundImage = ImageIO.read(new File("src/images/dark_background.png"));
            System.out.println("Image loaded");
        } catch (IOException e) {
            System.out.println("Could not load image");
        }
        g.drawImage(backgroundImage, 0, 0, width, height, this);
    }

    private int radianToDegrees(double radians) {
        return (int) ceil(radians * 180 / Math.PI);
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
        isUpdating = true;
        currentPosition = 0;
        currentAngle = 0;
        dataTotal = 0;
        for(Map.Entry<String, Integer> entry: data.entrySet()) {
            dataTotal += entry.getValue();
        }
        repaint();
    }
}