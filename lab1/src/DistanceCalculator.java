import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DistanceCalculator extends JFrame {
    private JTextField lat1Field, lat2Field, lon1Field, lon2Field;
    private JButton solveButton, clearButton;
    private JLabel resultLabel;
    public DistanceCalculator() {
        setTitle("Відстань між точками");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        addLabels();
        addTextFields();
        addButtons();
        addResultLabel();
    }

    private void addLabels() {
        JLabel lat1Label = new JLabel("Широта 1 (град.):");
        lat1Label.setBounds(10, 10, 150, 25);
        add(lat1Label);

        JLabel lon1Label = new JLabel("Довгота 1 (град.):");
        lon1Label.setBounds(10, 40, 150, 25);
        add(lon1Label);

        JLabel lat2Label = new JLabel("Широта 2 (град.):");
        lat2Label.setBounds(10, 70, 150, 25);
        add(lat2Label);

        JLabel lon2Label = new JLabel("Довгота 2 (град.):");
        lon2Label.setBounds(10, 100, 150, 25);
        add(lon2Label);
    }

    private void addTextFields() {
        lat1Field = new JTextField();
        lat1Field.setBounds(160, 10, 150, 25);
        add(lat1Field);

        lon1Field = new JTextField();
        lon1Field.setBounds(160, 40, 150, 25);
        add(lon1Field);

        lat2Field = new JTextField();
        lat2Field.setBounds(160, 70, 150, 25);
        add(lat2Field);

        lon2Field = new JTextField();
        lon2Field.setBounds(160, 100, 150, 25);
        add(lon2Field);
    }

    private void addButtons() {
        solveButton = new JButton("Обчислити");
        solveButton.setBounds(10, 130, 150, 25);
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateDistance();
            }
        });
        add(solveButton);

        clearButton = new JButton("Очистити");
        clearButton.setBounds(160, 130, 150, 25);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lat1Field.setText("");
                lon1Field.setText("");
                lat2Field.setText("");
                lon2Field.setText("");
                resultLabel.setText("");
            }
        });
        add(clearButton);
    }

    private void addResultLabel() {
        resultLabel = new JLabel("");
        resultLabel.setBounds(10, 160, 300, 25);
        add(resultLabel);
    }

    private void calculateDistance() {
        double lat1 = Math.toRadians(Double.parseDouble(lat1Field.getText()));
        double lon1 = Math.toRadians(Double.parseDouble(lon1Field.getText()));
        double lat2 = Math.toRadians(Double.parseDouble(lat2Field.getText()));
        double lon2 = Math.toRadians(Double.parseDouble(lon2Field.getText()));

        double earthRadius = 6371.01; // Кілометри
        double distance = earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        resultLabel.setText("Відстань: " + distance + " км");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DistanceCalculator().setVisible(true);
            }
        });
    }
}