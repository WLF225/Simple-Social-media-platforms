package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;

public class SaveToFiles implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {
        //This try is to make sure the user read all the 3 files

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(null);
        alert.setContentText("You need to chose user file then friends file then posts file or you will get an error.");
        alert.showAndWait();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chose Users file");

        File userFile = fileChooser.showSaveDialog(null);
        if (userFile == null) {
            throw new AlertException("Couldn't find any file.");
        }

        fileChooser.setTitle("Chose Friends file");
        File friendsFile = fileChooser.showSaveDialog(null);
        if (friendsFile == null) {
            throw new AlertException("Friends file and posts file not found.");
        }

        fileChooser.setTitle("Chose Posts file");
        File postsFile = fileChooser.showSaveDialog(null);
        if (postsFile == null) {
            throw new AlertException("Posts file not found.");
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("How do you want the report to be sorted based on username ?");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Ascending", "Descending");
        comboBox.getSelectionModel().selectFirst();
        alert.getDialogPane().setContent(comboBox);
        if (alert.showAndWait().get() == ButtonType.OK) {
            try (PrintWriter userPR = new PrintWriter(userFile);
                 PrintWriter friendsPR = new PrintWriter(friendsFile);
                 PrintWriter postsFR = new PrintWriter(postsFile);) {

                userPR.println("User ID,Name,Age");
                friendsPR.println("User ID,Friends");
                postsFR.println("Posts ID,Creator ID,Content,Creation Date,Shared With");

                ListIterator<User> currUser = Main.userList.iterator();

                if (comboBox.getValue().equals("Ascending")) {
                    //To print the users
                    while (currUser.hasNext()) {
                        User user = currUser.next();
                        userPR.println(user.toString());
                        ListIterator<Post> currPost = user.getPosts().iterator();
                        //To print friends
                        if (!user.getFriends().isEmpty())
                            friendsPR.println(user.printFriends());
                        //To print the posts
                        while (currPost.hasNext()) {
                            postsFR.println(currPost.next().toString());
                        }
                    }
                }else {
                    //To print the users
                    while (currUser.hasPrevious()) {
                        User user = currUser.previous();
                        userPR.println(user.toString());
                        ListIterator<Post> currPost = user.getPosts().iterator();
                        //To print friends
                        if (!user.getFriends().isEmpty())
                            friendsPR.println(user.printFriends());
                        //To print the posts
                        while (currPost.hasNext()) {
                            postsFR.println(currPost.next().toString());
                        }
                    }
                }
            } catch (IOException e) {

            }
        }
    }
}