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

    public Pastel(BufferedImage _bufferedImage, int _width, int _height, Map<String, Integer> _data) {
        this.bufferedImage = _bufferedImage;
        this.width = _width;
        this.height = _height;
        this.data = _data;
        dataTotal = data.values().stream().mapToInt(Integer::parseInt).sum();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for(Map.Entry<String, Integer> entry: data.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            paintSection(graphics2D);
        }
        g.drawImage(bufferedImage, 0, 0, this);
        super.paintComponent(g);
    }

    private void paintSection(Graphics2D graphics2D) {

    }
}
