package models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

public class Bars extends JPanel {
    private final BufferedImage bufferedImage;
    private final int width, height;
    private final Map<String, Integer> data;
    private int maxValue = 0;
    private final int positionDelta;
    private int currentPosition = 50;

    public Bars(BufferedImage _bufferedImage, int _width, int _height, Map<String, Integer> _data) {
        this.bufferedImage = _bufferedImage;
        this.width = _width;
        this.height = _height;
        this.data = _data;

        for(Map.Entry<String, Integer> entry: data.entrySet()) {
            if(entry.getValue() > maxValue) {
                this.maxValue = entry.getValue();
            }
        }

        this.positionDelta = (width - 100) / data.size();

        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        drawAxis(graphics2D);
        int index = 0;
        for(Map.Entry<String, Integer> entry: data.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            drawBar(graphics2D, entry);
            index++;
        }
        currentPosition = 50;
        g.drawImage(bufferedImage, 0, 0, this);
    }

    private void drawBar(Graphics2D graphics2D, Map.Entry<String, Integer> entry) {
        int maxHeight = height - 120;

        TexturePaint texturePaint = new TexturePaint(
                generateRandomShapeTexture((positionDelta - 10), (positionDelta - 10)),
                new Rectangle(0, 0, (positionDelta - 10), (positionDelta - 10))
        );

        graphics2D.setPaint(texturePaint);
        graphics2D.fillRect(currentPosition, height - 50 - (entry.getValue() * maxHeight / maxValue), positionDelta - 10, (entry.getValue() * maxHeight / maxValue));
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(currentPosition + 8, height - 80, positionDelta - 30, 20);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(entry.getKey(), currentPosition + 15, height - 65);
        currentPosition += positionDelta;
    }
    private void drawAxis(Graphics2D graphics2D) {
        graphics2D.drawRect(20, 20, width - 40, height - 40);
        graphics2D.drawLine(40, 50, 40, height - 40);
        graphics2D.drawLine(40, 50, 35, 55);
        graphics2D.drawLine(40, 50, 45, 55);
        graphics2D.drawString("Porcentaje", 25, 40);
        graphics2D.drawLine(40, height - 40, width - 50, height - 40);
        graphics2D.drawLine(width - 50, height - 40, width - 55, height - 45);
        graphics2D.drawLine(width - 50, height - 40, width - 55, height - 35);
    }

    public static BufferedImage generateRandomShapeTexture(int width, int height) {
        BufferedImage texture = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = texture.createGraphics();
        Random random = new Random();

        Color bgColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, width, height);

        for (int i = 0; i < 10; i++) {
            drawRandomPolygon(g2d, width, height, random);
        }

        g2d.dispose();
        return texture;
    }

    private static void drawRandomPolygon(Graphics2D g2d, int width, int height, Random random) {
        Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        g2d.setColor(color);

        int sides = 3 + random.nextInt(4); // Between 3 and 6 sides
        Polygon polygon = new Polygon();
        for (int i = 0; i < sides; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            polygon.addPoint(x, y);
        }

        g2d.fillPolygon(polygon);
    }
}