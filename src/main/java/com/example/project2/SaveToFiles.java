package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.PrintWriter;
import java.util.ListIterator;

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

            ListIterator<User> currUser = Main.userList.iterator();

            //To print the users
            while (currUser.hasNext()) {
                User user = currUser.next();
                userPR.println(user.print());
                ListIterator<Post> currPost = user.getPosts().iterator();
                //To print friends
                if (!user.getFriends().isEmpty())
                    friendsPR.println(user.printFriends());
                //To print the posts
                while (currPost.hasNext()){
                    postsFR.println(currPost.next().print());
                }
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