package com.example.project2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReportingAndStatistics extends Pane {

    boolean saved = false;

    public ReportingAndStatistics(Stage stage) {

        stage.setTitle("Reporting and Statistics");

        Button[] buttons = {new Button("View Users"),new Button("Posts Created by specific User"),
                new Button("Posts shared with specific User"), new Button("Statistical reports"),
                new Button("Save"), new Button("Loge out"), new Button("Exit")};

        MainMenu.styling(buttons,30);

        ImageView imageView = new ImageView(new Image("view.png"));
        imageView.setLayoutX(1250);
        imageView.setLayoutY(100);

        buttons[0].setOnAction(e ->
                stage.setScene(new Scene(new UsersManagement(stage,Main.userList,null,false))));

        buttons[1].setOnAction(event -> {
            //To make the user enter the user id he want to see his posts
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Enter the user ID you want to view his posts");
            TextField userID = new TextField();
            userID.setPromptText("User ID");
            alert.getDialogPane().setContent(userID);

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            try {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    int id = Integer.parseInt(userID.getText());

                    User user = Main.getUserFromID(id);

                    //To make sure he entered existing user id
                    if (user == null)
                        throw new AlertException("User not found");

                    stage.setScene(new Scene(new PostManagement(stage, user.getPosts(),null, false)));

                }
            }catch (NumberFormatException e) {
                error.setContentText("Please enter a valid user ID");
                error.showAndWait();
            }catch (AlertException e) {
                error.setContentText(e.getMessage());
                error.showAndWait();
            }
        });

        buttons[2].setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Enter the user ID you want to view posts shared with him");
            TextField userID = new TextField();
            userID.setPromptText("User ID");
            alert.getDialogPane().setContent(userID);

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            try {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    int id = Integer.parseInt(userID.getText());

                    User user = Main.getUserFromID(id);

                    //To make sure he entered existing user id
                    if (user == null)
                        throw new AlertException("User not found");

                    stage.setScene(new Scene(new PostManagement(stage, user.getPostsSharedWith(),null, false)));

                }
            }catch (NumberFormatException e) {
                error.setContentText("Please enter a valid user ID");
                error.showAndWait();
            }catch (AlertException e) {
                error.setContentText(e.getMessage());
                error.showAndWait();
            }
        });

        buttons[3].setOnAction(event ->
                stage.setScene(new Scene(new Reports(stage))));

        buttons[4].setOnAction(e -> {
            try {
                new SaveToFiles().handle(null);
                saved = true;
            }catch (AlertException e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e1.getMessage());
                alert.showAndWait();
            }

        });

        buttons[5].setOnAction(e -> stage.setScene(new Scene(new MainMenu(stage))));

        buttons[6].setOnAction(e -> {
            if (!saved) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to exit without saving");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    System.exit(0);
                }
                return;
            }
            System.exit(0);
        });

        VBox vbox = new VBox(30,buttons[0],buttons[1],buttons[2],buttons[3]);
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX(100);
        vbox.setLayoutY(200);

        HBox hbox = new HBox(30,buttons[4],buttons[5],buttons[6]);
        hbox.setLayoutX(1250);
        hbox.setLayoutY(900);

        getChildren().addAll(vbox,hbox,imageView);

    }
}
