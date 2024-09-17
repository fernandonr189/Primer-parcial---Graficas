package models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Canvas extends JFrame {

    private BufferedImage pastelBuffer;

    public Canvas(int width, int height) {
        BufferedImage backgroundBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pastelBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        setTitle("Paisaje");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Map<String, Integer> capitalCities = new HashMap<String, Integer>();

        // Add keys and values (Country, City)
        capitalCities.put("England", 10);
        capitalCities.put("Germany", 20);
        capitalCities.put("Norway", 30);
        capitalCities.put("USA", 40);
        capitalCities.put("Mexico", 20);
        capitalCities.put("Argentina", 20);

        Pastel pastel = new Pastel(pastelBuffer, width, height, capitalCities);
        add(pastel);
        setVisible(true);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}