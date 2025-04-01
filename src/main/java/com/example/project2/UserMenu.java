package com.example.project2;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserMenu extends Pane {

    private boolean saved = false;
    private Integer deleted;
    User user;

    public UserMenu(Stage stage, User user) {

        stage.setTitle(user.getName()+" account");

        this.user = user;
        Button[] buttons = {new Button("Posts shared with me"), new Button("Posts management"),
                new Button("Friends management"), new Button("Edit my profile"), new Button("Delete my user"),
                new Button("Save"), new Button("Loge out"), new Button("Exit")};

        ImageView imageView = new ImageView(new Image("user.png"));

        MainMenu.styling(buttons, 35);

        VBox vbox = new VBox(40, buttons[0], buttons[1], buttons[2], buttons[3], buttons[4]);
        vbox.setLayoutX(100);
        vbox.setLayoutY(100);

        HBox hbox = new HBox(30, buttons[6], buttons[5], buttons[7]);
        hbox.setLayoutX(1100);
        hbox.setLayoutY(900);

        imageView.setLayoutX(1100);
        imageView.setLayoutY(100);

        buttons[0].setOnAction(e -> stage.setScene(new Scene(new SharedPost(stage,user))));

        buttons[1].setOnAction(e -> stage.setScene(new Scene(new PostManagement(stage,user))));

        buttons[2].setOnAction(event -> stage.setScene(new Scene(new FriendManagement(stage,user))));

        buttons[3].setOnAction(e -> stage.setScene(new Scene(new EditUser(stage,user))));

        buttons[4].setOnAction(event -> {
            new DeleteUser(user.getId(), true).handle(null);
            this.user = Main.getUserFromID(user.getId());
            if (this.user == null) {
                buttons[6].fire();
            }
        });

        buttons[5].setOnAction(event -> {
            new SaveToFiles().handle(null);
            saved = true;
        });

        buttons[6].setOnAction(event -> {
            stage.setScene(new Scene(new MainMenu(stage)));
        });

        buttons[7].setOnAction(event -> {
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

        getChildren().addAll(vbox, hbox, imageView);
    }

}
