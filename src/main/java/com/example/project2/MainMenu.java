package com.example.project2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends BorderPane {

    private boolean saved = false;

    public MainMenu(Stage stage) {
        stage.setTitle("Main Menu");

        Button[] buttons = {new Button("Enter as a user"), new Button("Reporting and statistics"),
                new Button("Read all the files"), new Button("Save to files"), new Button("Exit")};


        styling(buttons, 30);

        buttons[0].setOnAction(e -> {
            boolean cond = true;
            //To remind the user to read files
            if (Main.userList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to enter without reading files ?, " +
                        "you can only create new account and it may have wrong ID");
                cond = alert.showAndWait().get().equals(ButtonType.OK);
            }
            if (cond) {
                //To make the user loge in or sign up
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Join as a user");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("How you want to join ?");
                ButtonType logeInBT = new ButtonType("loge in");
                ButtonType signUpBT = new ButtonType("Sign up");
                confirmationAlert.getButtonTypes().setAll(logeInBT, signUpBT, ButtonType.CANCEL);

                ButtonType bT = confirmationAlert.showAndWait().get();
                if (bT == ButtonType.CANCEL) {
                    return;
                }
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);

                cond = bT == logeInBT;
                //To enter the loge in menu
                if (cond) {
                    if (Main.userList.isEmpty()) {
                        errorAlert.setContentText("You can't enter without reading files or create new account.");
                        errorAlert.showAndWait();
                        return;
                    }
                    Alert confirmationAlert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert2.setTitle("Confirmation");
                    confirmationAlert2.setHeaderText("Enter The ID for your user");
                    TextField idTF = new TextField();
                    idTF.setPromptText("Enter The ID");
                    confirmationAlert2.getDialogPane().setContent(new Pane(idTF));

                    //To make him enter his ID and make sure its not wrong
                    if (confirmationAlert2.showAndWait().get().equals(ButtonType.OK)) {
                        try {
                            int id = Integer.parseInt(idTF.getText());
                            User user = Main.getUserFromID(id);
                            if (user == null) {
                                errorAlert.setContentText("The account you are trying to access does not exist.");
                                errorAlert.showAndWait();
                                return;
                            }
                            stage.setScene(new Scene(new UserMenu(stage, user)));

                        } catch (NumberFormatException e1) {
                            errorAlert.setContentText("ID text field should be an integer");
                            errorAlert.showAndWait();
                        }
                    }
                } else {
                    stage.setScene(new Scene(new CreateNewUser(stage, this)));
                }
            }
        });

        buttons[1].setOnAction(e -> {
            boolean cond = true;
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            //To remind the user to read files
            if (Main.userList.isEmpty()) {
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Are you sure you want to enter without reading files ?");
                cond = confirmationAlert.showAndWait().get().equals(ButtonType.OK);
            }
            if (cond)
                stage.setScene(new Scene(new ReportingAndStatistics(stage)));

    });

    buttons[2].

    setOnAction(new ReadFiles());

    buttons[3].

    setOnAction(event ->

    {
        try {
            new SaveToFiles().handle(null);
            saved = true;
        } catch (AlertException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    });

    buttons[4].

    setOnAction(event ->

    {
        boolean cond = true;
        //To remember the user by saving
        if (!saved) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to exit without save changes ?");
            cond = alert.showAndWait().get() == ButtonType.OK;
        }
        if (cond) {
            System.exit(0);
        }
    });


    VBox vbox = new VBox(30, buttons[0], buttons[1]);
    HBox hbox = new HBox(30, buttons[2], buttons[3], buttons[4]);
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
