package com.example.project2;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ReportingAndStatistics extends Pane {

    boolean saved = false;

    public ReportingAndStatistics(Stage stage) {

        Button[] buttons = {new Button("Users management"),new Button("Posts management"),new Button("Statistical reports"),
                new Button("Save"), new Button("Loge out"), new Button("Exit")};



        buttons[3].setOnAction(event -> {
            try {
                new SaveToFiles().handle(null);
                saved = true;
            }catch (AlertException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        buttons[4].setOnAction(event -> stage.setScene(new Scene(new MainMenu(stage))));

        buttons[5].setOnAction(event -> {
            boolean cond = true;
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


    }
}
