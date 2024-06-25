package lab_4;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualInterfaceController implements Initializable {

    @FXML
    public ChoiceBox<String> fontPicker;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    private Label myLabel;

    public String changeCase(String string) {

        char ch;
        StringBuilder result = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); i++) {
            int rand = (int) ((Math.random()*10)%2);
            ch = rand == 0? Character.toUpperCase(string.charAt(i)) : Character.toLowerCase(string.charAt(i));
            result.append(ch);
        }

        return result.toString();
    }

    public void startAnimation() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            myLabel.setLayoutX(myLabel.getLayoutX()+4);
            myLabel.setLayoutY(myLabel.getLayoutY()+3);
            if (myLabel.getLayoutX()>=anchorPane.getWidth()-myLabel.getWidth()) {
                myLabel.setLayoutX(0);
                myLabel.setLayoutY(0);
                myLabel.setText(changeCase(myLabel.getText()));
            }
        }), new KeyFrame(Duration.millis(50)));

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void setFontPicker() {

        fontPicker.setValue(String.valueOf(myLabel.getFont().getFamily()));
        fontPicker.getItems().addAll(Font.getFamilies());

        fontPicker.setOnAction(event -> {
            myLabel.setFont(Font.font(fontPicker.getValue()));
        });
    }

    public void initialize(URL location, ResourceBundle resources) {

        setFontPicker();
        startAnimation();
    }
}