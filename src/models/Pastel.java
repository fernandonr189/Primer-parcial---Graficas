package models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Pastel extends JPanel {

    private BufferedImage bufferedImage;
    private int width, height;
    private Map<String, Integer> data;
    private int dataTotal = 0;
    private double currentAngle = 0;
    private Point center;
    private final int radius = 250;
    private final Color[] colors = {Color.RED, Color.ORANGE, Color.BLUE, Color.YELLOW};

    public Pastel(BufferedImage _bufferedImage, int _width, int _height, Map<String, Integer> _data) {
        this.bufferedImage = _bufferedImage;
        this.width = _width;
        this.height = _height;
        this.data = _data;
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
            System.out.println(entry.getKey() + " " + entry.getValue());
            paintSection(graphics2D, entry, colors[index]);
            index++;
        }
        g.drawImage(bufferedImage, 0, 0, this);
    }

    private void paintSection(Graphics2D graphics2D, Map.Entry<String, Integer> entry, Color color) {
        double angleDelta = (entry.getValue() * 2 * Math.PI) / dataTotal;

        Point startingPoint = new Point(
                (int) (radius * Math.cos(currentAngle) + center.x),
                (int) (radius * Math.sin(currentAngle) + center.y)
        );

        Point finishingPoint = new Point(
                (int) (radius * Math.cos(currentAngle + angleDelta) + center.x),
                (int) (radius * Math.sin(currentAngle + angleDelta) + center.y));

        graphics2D.setColor(color);

        Polygon shape = new Polygon(
                new int[]{
                        center.x,
                        startingPoint.x,
                        finishingPoint.x},
                new int[]{
                        center.y,
                        startingPoint.y,
                        finishingPoint.y},
                3);
        graphics2D.fillPolygon(shape);

        currentAngle = currentAngle + angleDelta;
    }
}