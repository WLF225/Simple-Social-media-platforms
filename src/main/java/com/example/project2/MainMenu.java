package com.example.project2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends BorderPane {

    public MainMenu(Stage stage) {
        stage.setTitle("Main Menu");

        Button[] buttons = {new Button("Enter as a user"), new Button("Enter as an administrator"),
                new Button("Read all the files"), new Button("Save to files")};


        styling(buttons, 30);

        buttons[0].setOnAction(e -> {
            boolean cond = true;
            if (Main.userList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to enter without reading files ?, " +
                        "you can only create new account and it may have wrong ID");
                cond = alert.showAndWait().get().equals(ButtonType.OK);
            }
            if (cond) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Join as a user");
                alert.setHeaderText(null);
                alert.setContentText("How you want to join ?");
                ButtonType logeInBT = new ButtonType("loge in");
                ButtonType signUpBT = new ButtonType("Sign up");
                alert.getButtonTypes().setAll(logeInBT, signUpBT, ButtonType.CANCEL);

                ButtonType bT = alert.showAndWait().get();
                if (bT == ButtonType.CANCEL) {
                    return;
                }
                cond = bT == logeInBT;
                if (cond) {
                    if (Main.userList.isEmpty()) {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Error");
                        alert1.setHeaderText(null);
                        alert1.setContentText("You can't enter without reading files or create new account.");
                        alert1.showAndWait();
                        return;
                    }
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setTitle("Confirmation");
                    alert1.setHeaderText("Enter The ID for your user");
                    TextField idTF = new TextField();
                    idTF.setPromptText("Enter The ID");
                    alert1.getDialogPane().setContent(new Pane(idTF));
                    alert1.showAndWait();
                    System.out.println(idTF.getText());
                } else {
                    stage.setScene(new Scene(new CreateNewUser(stage, this)));
                }
            }
        });

        buttons[2].setOnAction(new ReadFiles());

        buttons[3].setOnAction(new SaveToFiles());


        VBox vbox = new VBox(30, buttons[0], buttons[1]);
        HBox hbox = new HBox(30, buttons[2], buttons[3]);
        hbox.setAlignment(Pos.TOP_RIGHT);

        setLeft(vbox);
        setBottom(hbox);


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
