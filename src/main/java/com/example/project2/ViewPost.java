package com.example.project2;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ListIterator;

public class ViewPost extends Pane {

    Post post;

    public ViewPost(Stage stage, DLinkedList<Post> posts,User user ,int postID, boolean manage) {

        stage.setTitle("View Posts");

        ImageView imageView = new ImageView(new Image("viewPost.png"));
        imageView.setLayoutX(1300);
        imageView.setLayoutY(100);

        Button[] buttons = {new Button("Previse"), new Button("Next"), new Button("Edit content"),
                new Button("Delete"), new Button("Back")};

        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-font-size: 25;");
        datePicker.setDisable(true);

        MainMenu.styling(buttons, 30);

        TextField[] textFields = {new TextField(), new TextField(), new TextField(), new TextField()};

        for (TextField textField : textFields) {
            textField.setFont(Font.font(25));
            textField.setDisable(true);
        }

        textFields[2].setDisable(false);

        Label[] labels = {new Label("Post ID: "), new Label("Creator ID: "), new Label("Content: "),
                new Label("Date: "), new Label("Shared with: ")};

        for (Label label : labels) {
            label.setFont(Font.font(30));
        }

        ListIterator<Post> currPost = posts.iterator();

        buttons[0].setOnAction(event -> {
            post = currPost.previous();
            if (buttons[1].isDisable())
                buttons[1].setDisable(false);

            if (!currPost.hasPrevious())
                buttons[0].setDisable(true);

            textFields[0].setText(post.getId() + "");
            textFields[1].setText(post.getCreatorID() + "");
            textFields[2].setText(post.getContent());
            textFields[3].setText(post.sharedToToString().replaceFirst(",", ""));
            LocalDate localDate = post.getDate().toZonedDateTime().toLocalDate();
            datePicker.setValue(localDate);
        });

        buttons[1].setOnAction(event -> {
            post = currPost.next();
            if (buttons[0].isDisable())
                buttons[0].setDisable(false);

            if (!currPost.hasNext())
                buttons[1].setDisable(true);

            textFields[0].setText(post.getId() + "");
            textFields[1].setText(post.getCreatorID() + "");
            textFields[2].setText(post.getContent());
            textFields[3].setText(post.sharedToToString().replaceFirst(",", ""));
            LocalDate localDate = post.getDate().toZonedDateTime().toLocalDate();
            datePicker.setValue(localDate);
        });
        if (manage) {
            buttons[2].setOnAction(event -> {
                post.setContent(textFields[2].getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit content");
                alert.setHeaderText(null);
                alert.setContentText("The post content edited successfully.");
                alert.showAndWait();
            });
        }
        if (manage) {
        buttons[3].setOnAction(event -> {
            try {
                int id = Integer.parseInt(textFields[0].getText());

                new DeletePost(id).handle(null);
                Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                informationAlert.setTitle("Success");
                informationAlert.setHeaderText(null);
                informationAlert.setContentText("Successfully deleted post");
                informationAlert.showAndWait();

                buttons[4].fire();
            } catch (AlertException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText(ex.getMessage());
                errorAlert.showAndWait();
            }
        });
}
        if (manage)
            buttons[4].setOnAction(event -> stage.setScene(new Scene(new PostManagement(stage, posts,user,true))));
        else
            buttons[4].setOnAction(event -> stage.setScene(new Scene(new PostManagement(stage, posts,user,false))));

        //To find the post with his iterator so we can use next and previse work
        while (currPost.hasNext()) {
            buttons[1].fire();
            if (post.getId() == postID) {
                if (!currPost.hasPrevious())
                    buttons[0].setDisable(true);
                break;
            }
        }
        if (!manage) {
            textFields[2].setEditable(false);
        }

        VBox labelsVB;
        VBox textFieldsVB;
        if (manage) {
             labelsVB = new VBox(40, labels[0], labels[1], labels[2], labels[3], labels[4]);
             textFieldsVB = new VBox(30, textFields[0], textFields[1], textFields[2], datePicker, textFields[3]);
        }else {
            labelsVB = new VBox(40, labels[0], labels[1], labels[2], labels[3]);
            textFieldsVB = new VBox(30, textFields[0], textFields[1], textFields[2], datePicker);
        }

        HBox mainHBox = new HBox(30, labelsVB, textFieldsVB);

        VBox mainVB = new VBox(30, mainHBox);
        mainVB.setLayoutX(100);
        mainVB.setLayoutY(200);

        HBox nextPrevHB = new HBox(50, buttons[0], buttons[1]);
        nextPrevHB.setLayoutX(100);
        nextPrevHB.setLayoutY(700);
        HBox buttonsHB;
        if (manage) {
            buttonsHB = new HBox(30, buttons[2], buttons[3], buttons[4]);
            buttonsHB.setLayoutX(1300);
            buttonsHB.setLayoutY(900);
        }else {
            buttonsHB = new HBox(30, buttons[4]);
            buttonsHB.setLayoutX(1500);
            buttonsHB.setLayoutY(900);
        }

        getChildren().addAll(mainVB, buttonsHB, imageView, nextPrevHB);

    }
}
