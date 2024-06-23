package Lab2;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.Objects;



public class Lab2 extends Application {       //створуємо нову програму
    @Override
    public void start(Stage stage) throws Exception {
        Parent root ;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Lab2.fxml"))); //підключаємо фхмл файл

        stage.setScene(new Scene(root));   //встановлюємо сцену
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}