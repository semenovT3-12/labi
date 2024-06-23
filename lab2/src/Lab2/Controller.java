package Lab2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class Controller {

    @FXML
    public TextField city1;
    @FXML
    public TextField city2;
    @FXML
    public TextField city3;
    @FXML
    public TextField city4;
    @FXML
    public TextField result1;
    @FXML
    public Label label2;
    @FXML
    public Label label1;
    @FXML
    public Button resultbutton;
    @FXML
    public Button clearbutton;
    @FXML
    public Label resultlabel;
    @FXML
    public AnchorPane main;


    @FXML
    private void calculateDistance() {     //створили логіку для кнопки
        try {
            double lat1 = Double.parseDouble(city1.getText());
            double lon1 = Double.parseDouble(city2.getText());
            double lat2 = Double.parseDouble(city3.getText());
            double lon2 = Double.parseDouble(city4.getText());



            double dlon = lon2 - lon1;
            double dlat = lat2 - lat1;
            double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = 6371 * c; // Радіус Землі в кілометрах

//
            result1.setText(String.format("%.2f км", distance)); // Вивід результату у кілометрах з округленням до двох знаків після коми

        } catch (NumberFormatException e) {
            System.err.println("Invalid input format!"); // Повідомлення про помилку у форматі введення
        }
    }



    @FXML
    private void clearFields() {     //створюємо логіку для кнопки
        city4.clear();
        city3.clear();
        city2.clear();
        city1.clear();
        result1.clear();
    }
}