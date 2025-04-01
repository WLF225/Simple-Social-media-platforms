package com.example.project2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SharedPost extends BorderPane {
    public SharedPost(Stage stage, User user) {

        stage.setTitle("Shared Posts with "+user.getName());

        ObservableList<Post> postsList = FXCollections.observableArrayList();
        user.getPostsSharedWith().addToObservableList(postsList);

        TableView<Post> tableView = new TableView<>(postsList);

        TableColumn<Post, Integer> postIdTC = new TableColumn<>("Post ID");
        postIdTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        postIdTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        postIdTC.setMinWidth(300);

        TableColumn<Post, Integer> postCreatorTC = new TableColumn<>("Creator ID");
        postCreatorTC.setCellValueFactory(new PropertyValueFactory<>("creatorID"));
        postCreatorTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        postCreatorTC.setMinWidth(300);

        TableColumn<Post,String> postCreatorNameTC = new TableColumn<>("Creator Name");
        postCreatorNameTC.setCellValueFactory(new PropertyValueFactory<>("creatorName"));
        postCreatorNameTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        postCreatorNameTC.setMinWidth(400);

        TableColumn<Post, String> postContentTC = new TableColumn<>("Post Content");
        postContentTC.setCellValueFactory(new PropertyValueFactory<>("content"));
        postContentTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        postContentTC.setMinWidth(800);

        TableColumn<Post, String> postDateTC = new TableColumn<>("Post Date");
        postDateTC.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().dateToString()));
        postDateTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        postDateTC.setMinWidth(300);

        tableView.getColumns().addAll(postIdTC, postCreatorTC, postCreatorNameTC, postContentTC, postDateTC);

        setCenter(tableView);

        Button[] buttons = {new Button("Search for a post by its Content"),new Button("Back")};
        MainMenu.styling(buttons,40);

        TextField textField = new TextField();
        textField.setFont(Font.font(30));

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

        buttons[1].setOnAction(event -> stage.setScene(new Scene(new UserMenu(stage,user))));

        setBottom(buttons[1]);
        BorderPane.setAlignment(buttons[1], Pos.BOTTOM_RIGHT);

        VBox vBox = new VBox(30,textField,buttons[0]);
        setTop(vBox);

    }
}
