package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        Map<String, Integer> capitalCities = getCities();
        Cake cake = new Cake(pastelBuffer, width - 200, height, capitalCities);
        Bars bars = new Bars(barrasBuffer, width - 200, height, capitalCities);

        String[] options = {"Barras", "Pastel"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setBounds(width - 200, 50, 100, 25);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selectedElement = (String) comboBox.getSelectedItem();

                switch (selectedElement) {
                    case "Barras":
                        remove(cake);
                        add(bars);
                        repaint();
                        break;
                    case "Pastel":
                        remove(bars);
                        add(cake);
                        repaint();
                        break;
                }
            }
        });

        add(comboBox);
        add(cake);
        add(bars);

        setVisible(true);
    }

    private Map<String, Integer> getCities() {
        Map<String, Integer> capitalCities = new HashMap<String, Integer>();

        // Add keys and values (Country, City)
        capitalCities.put("England", 10);
        capitalCities.put("Germany", 20);
        capitalCities.put("Norway", 30);
        capitalCities.put("USA", 40);
        capitalCities.put("Mexico", 20);
        return capitalCities;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}