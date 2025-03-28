package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class DeletePost implements EventHandler<ActionEvent> {

    TextField tf;
    int postID;

    public DeletePost(TextField tf) {
        this.tf = tf;
    }
    public DeletePost(int id) {
        this.postID = id;
    }

    public void handle(ActionEvent event) {

        if (tf != null)
             postID = Integer.parseInt(tf.getText());

        deletePost(postID,true);

    }
    public void deletePost(int postID,boolean confirmation) {
        if (postID < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid post ID");
            alert.showAndWait();
            return;
        }
        DNode<User> currUser = Main.userList.getHead().getNext();
        //This is to find the post
        DNode<Post> currPost = null;
        boolean cond = false;

        //To find the post you want to delete with the user that have it
        while (currUser != Main.userList.getHead()) {
            currPost = currUser.getData().getPosts().getHead().getNext();
            while (currPost != currUser.getData().getPosts().getHead()) {
                if (currPost.getData().getId() == postID) {
                    cond = true;
                    break;
                }
                currPost = currPost.getNext();
            }
            if (cond)
                break;

            currUser = currUser.getNext();
        }
        if (!cond) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The post does not exists");
            alert.showAndWait();
            return;
        }
        boolean ok = true;
        if (confirmation) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the post with id " + postID + " ?");
            ok = alert.showAndWait().get() == ButtonType.OK;
        }
        if (ok) {

            DNode<Integer> currInt = currPost.getData().getSharedTo().getHead().getNext();
            //To delete the post from the other users shared with
            while (currInt != currPost.getData().getSharedTo().getHead()) {
                DNode<User> user = Main.getUserFromID(currInt.getData());
                user.getData().getPostsSharedWith().delete(currPost.getData());
                currInt = currInt.getNext();
            }
            //To delete it from the creator
            currUser.getData().getPosts().delete(currPost.getData());

        }
    }
}
