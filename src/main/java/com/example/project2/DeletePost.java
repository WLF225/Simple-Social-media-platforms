package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Iterator;

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
    public static void deletePost(int postID,boolean confirmation) {
        if (postID < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid post ID");
            alert.showAndWait();
            return;
        }
        Iterator<User> userIterator = Main.userList.iterator();
        //This is to find the post
        Iterator<Post> postIterator = null;
        boolean cond = false;

        //To find the post you want to delete with the user that have it
        Post post = null;
        User user = null;
        while (userIterator.hasNext()) {
            user = userIterator.next();
            postIterator = user.getPosts().iterator();
            while (postIterator.hasNext()) {
                post = postIterator.next();
                if (post.getId() == postID) {
                    cond = true;
                    break;
                }
            }
            if (cond)
                break;
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

            Iterator<Integer> integerIterator = post.getSharedTo().iterator();
            //To delete the post from the other users shared with
            while (integerIterator.hasNext()) {
                User user2 = Main.getUserFromID(integerIterator.next());
                user2.getPostsSharedWith().delete(post);
            }
            //To delete it from the creator
            user.getPosts().delete(post);

        }
    }
}
