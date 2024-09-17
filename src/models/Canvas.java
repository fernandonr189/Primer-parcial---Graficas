package models;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Canvas extends JFrame {

    private BufferedImage pastelBuffer;
    private BufferedImage barrasBuffer;
    private String[][] tableData;
    private Map<String, Integer> data;

    private final Cake cake;
    private final Bars bars;

    public Canvas(int width, int height) {
        pastelBuffer = new BufferedImage(width - 200, height, BufferedImage.TYPE_INT_ARGB);
        barrasBuffer = new BufferedImage(width - 200, height, BufferedImage.TYPE_INT_ARGB);
        setTitle("Graficas");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableData = new String[][]{
                {"Musica", "50"},
                {"Juegos", "120"},
                {"Fotos", "18"},
                {"Documentos", "10"},
                {"Videos", "47"}
        };
        getData();
        cake = new Cake(pastelBuffer, width - 200, height, data);
        bars = new Bars(barrasBuffer, width - 200, height, data);

        String[] options = {"Barras", "Pastel"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setBounds(width - 200, 50, 100, 25);


        String[] columnNames = {"Nombre", "Valor"};

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames) {
            @Override
            public void setValueAt(Object aValue, int row, int column) {
                switch (column) {
                    case 0:
                        if(aValue instanceof String) {
                            super.setValueAt(aValue, row, column);
                        }
                        break;
                    case 1:
                        try {
                            Integer.parseInt(aValue.toString());
                            super.setValueAt(aValue, row, column);
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Introduzca solo numeros");
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                updateTableData(tableModel);
                getData();
            }
        });

        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(width - 200, 150, 150, 150);

        add(scrollPane);
        add(comboBox);
        add(cake);
        add(bars);

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
        setVisible(true);
    }

    private void updateTableData(TableModel tableModel) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                tableData[i][j] = tableModel.getValueAt(i, j).toString();
            }
        }
    }

    private void getData() {
        Map<String, Integer> dictionary = new HashMap<String, Integer>();

        for(String[] item: tableData) {
            dictionary.put(item[0], Integer.parseInt(item[1]));
        }

        this.data = dictionary;
        if(cake != null && bars != null) {
            cake.setData(this.data);
            bars.setData(this.data);
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}