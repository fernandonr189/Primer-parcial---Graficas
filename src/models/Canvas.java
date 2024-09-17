package models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Canvas extends JFrame {

    private BufferedImage pastelBuffer;
    private BufferedImage barrasBuffer;

    public Canvas(int width, int height) {
        pastelBuffer = new BufferedImage(width - 200, height, BufferedImage.TYPE_INT_ARGB);
        barrasBuffer = new BufferedImage(width - 200, height, BufferedImage.TYPE_INT_ARGB);
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
        capitalCities.put("Mexico 2", 20);
        capitalCities.put("Argentina 3", 20);
        Cake cake = new Cake(pastelBuffer, width - 200, height, capitalCities);
        Bars bars = new Bars(barrasBuffer, width - 200, height, capitalCities);
        add(bars);
        setVisible(true);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}