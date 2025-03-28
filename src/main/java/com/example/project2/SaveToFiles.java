package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.PrintWriter;

public class SaveToFiles implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {
        //This try is to make sure the user read all the 3 files
        try {
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

            PrintWriter userPR = new PrintWriter(userFile);
            PrintWriter friendsPR = new PrintWriter(friendsFile);
            PrintWriter postsFR = new PrintWriter(postsFile);
            DNode<User> currUser = Main.userList.getHead().getNext();

            //To print the users
            while (currUser != Main.userList.getHead()) {
                userPR.println(currUser.getData().print());
                DNode<Post> currPost = currUser.getData().getPosts().getHead().getNext();
                //To print friends
                if (!currUser.getData().getFriends().isEmpty())
                    friendsPR.println(currUser.getData().printFriends());
                //To print the posts
                while (currPost != currUser.getData().getPosts().getHead()){
                    postsFR.println(currPost.getData().print());
                    currPost = currPost.getNext();
                }
                currUser = currUser.getNext();
            }
            userPR.close();
            friendsPR.close();
            postsFR.close();
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
