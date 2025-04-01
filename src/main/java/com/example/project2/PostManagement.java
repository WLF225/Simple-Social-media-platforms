package com.example.project2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PostManagement extends BorderPane {

    public PostManagement(Stage stage, User user) {

        stage.setTitle("Post Management for " + user.getName());

        Button[] buttons = {new Button("Search for a post by its content"),new Button("View post by its ID"),
                new Button("Create new post"), new Button("Back")};

        TextField textField = new TextField();

        MainMenu.styling(buttons,40);

        textField.setFont(Font.font(30));
        textField.setPromptText("Enter the post Content or ID");

        ObservableList<Post> postsList = FXCollections.observableArrayList();
        user.getPosts().addToObservableList(postsList);

        TableView<Post> tableView = new TableView<>(postsList);

        TableColumn<Post, Integer> postIdTC = new TableColumn<>("Post ID");
        postIdTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        postIdTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        postIdTC.setMinWidth(300);

        TableColumn<Post, Integer> postCreatorTC = new TableColumn<>("Creator ID");
        postCreatorTC.setCellValueFactory(new PropertyValueFactory<>("creatorID"));
        postCreatorTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        postCreatorTC.setMinWidth(300);

        TableColumn<Post, String> postContentTC = new TableColumn<>("Post Content");
        postContentTC.setCellValueFactory(new PropertyValueFactory<>("content"));
        postContentTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        postContentTC.setMinWidth(800);

        TableColumn<Post, String> postDateTC = new TableColumn<>("Post Date");
        postDateTC.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().dateToString()));
        postDateTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        postDateTC.setMinWidth(300);

        TableColumn<Post, String> sharedToTC = new TableColumn<>("Shared To");
        sharedToTC.setCellValueFactory(e ->
                new SimpleStringProperty(e.getValue().sharedToToString().replaceFirst(",","")));

        sharedToTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        sharedToTC.setMinWidth(500);

        tableView.getColumns().addAll(postIdTC, postCreatorTC, postContentTC, postDateTC, sharedToTC);

        setCenter(tableView);

        buttons[0].setTooltip(new Tooltip("Search for an empty text field to return to the normal table"));

        buttons[0].setOnAction(e ->{
            if (textField.getText().isEmpty()) {
                tableView.setItems(postsList);
            }else {
                boolean cond = false;
                ObservableList<Post> postsList2 = FXCollections.observableArrayList();
                for (Post post : postsList) {
                    if (post.getContent().toLowerCase().matches(".*"+textField.getText().toLowerCase()+".*")) {
                        postsList2.add(post);
                        cond = true;
                    }
                }
                if (cond) {
                    tableView.setItems(postsList2);
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Post Content does not match any post");
                    alert.showAndWait();
                }
            }

        });

        buttons[1].setOnAction(e -> {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            try {
                int postID = Integer.parseInt(textField.getText());
                boolean cond = false;
                //To make sure the user have the post with this id
                for (Post currPost : postsList) {
                    if (postID == currPost.getId()) {
                        cond = true;
                        break;
                    }
                }
                if (cond) {
                    stage.setScene(new Scene(new ViewPost(stage,user,postID)));
                }else {
                    errorAlert.setContentText("Post ID does not match any post of yours");
                    errorAlert.showAndWait();
                }

            }catch (NumberFormatException e1) {
                errorAlert.setContentText("Please enter a valid post ID");
                errorAlert.showAndWait();
            }
        });

        buttons[2].setOnAction(e -> stage.setScene(new Scene(new CreateNewPost(stage,user))));

        buttons[3].setOnAction(e -> stage.setScene(new Scene(new UserMenu(stage,user))));

        HBox hBox = new HBox(30,buttons[0],buttons[1],buttons[2]);
        VBox vbox = new VBox(30, textField, hBox);

        setTop(vbox);

        setBottom(buttons[3]);
        BorderPane.setAlignment(buttons[3], Pos.BOTTOM_RIGHT);

    }
}
