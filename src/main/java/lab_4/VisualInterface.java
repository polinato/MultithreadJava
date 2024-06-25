package lab_4;

/*
* Створити апплет з рядком, що рухається по діагоналі.
* При досягненні границь апплета всі символи рядка випадковим образом міняють регістр.
* При цьому шрифт міняється на шрифт, обраний зі списку.
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VisualInterface extends Application {

    public final String VIEW_NAME = "VisualInterfaceView.fxml";

    @Override
    public void start(Stage stage) {

        FXMLLoader fxmlLoader = null;
        try {
            if (VisualInterface.class.getResource(VIEW_NAME) == null) {
                throw new InvalidViewException("Empty View!");
            }
            else {
                fxmlLoader = new FXMLLoader(VisualInterface.class.getResource(VIEW_NAME));
            }
        }
        catch (InvalidViewException e) {
            System.out.println("Caught: " + e);
            System.exit(0);
        }

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Error loading FXML file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

class InvalidViewException extends Exception {

    private final String message;

    InvalidViewException(String error) {
        message = error;
    }

    @Override
    public String toString() {
        return message;
    }
}