package com.example.project2;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;


public class MainMenu extends BorderPane {

    public MainMenu() {
        Button[] buttons = {new Button("Read all the files")};
        styling(buttons,30);

        buttons[0].setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);

        });



        HBox hbox = new HBox(30,buttons);
        hbox.setAlignment(Pos.CENTER);
        setTop(hbox);

    }

    public static void styling(Button[] buttons, int textSize) {
        //To style the buttons
        for (Button button : buttons) {
            button.setStyle(
                    "-fx-background-color: #3498db; " + // Blue background
                            "-fx-text-fill: white; " +          // White text
                            "-fx-font-size: " + textSize + "px; " +           // Font size
                            "-fx-font-weight: bold; " +        // Bold text
                            "-fx-padding: 10px 20px; " +       // Padding
                            "-fx-border-radius: 0; " +         // Sharp corners
                            "-fx-background-radius: 0; " +     // Sharp background
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);" // Subtle shadow
            );

            button.setOnMouseEntered(e -> button.setStyle(
                    "-fx-background-color: #2980b9; " + // Darker blue on hover
                            "-fx-text-fill: white; " +
                            "-fx-font-size: " + textSize + "px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-border-radius: 0; " +
                            "-fx-background-radius: 0; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            ));

            // Revert to original style on exit
            button.setOnMouseExited(e -> button.setStyle(
                    "-fx-background-color: #3498db; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: " + textSize + "px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-border-radius: 0; " +
                            "-fx-background-radius: 0; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            ));

            // Pressed effect
            button.setOnMousePressed(e -> button.setStyle(
                    "-fx-background-color: #1c5980; " + // Even darker blue when pressed
                            "-fx-text-fill: white; " +
                            "-fx-font-size: " + textSize + "px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-border-radius: 0; " +
                            "-fx-background-radius: 0; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            ));

            // Revert to hover style on release
            button.setOnMouseReleased(e -> button.setStyle(
                    "-fx-background-color: #2980b9; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: " + textSize + "px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-border-radius: 0; " +
                            "-fx-background-radius: 0; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            ));
        }
    }
}
