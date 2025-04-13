package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ListIterator;

public class DeletePost implements EventHandler<ActionEvent> {

    int postID;

    public DeletePost(int id) {
        this.postID = id;
    }

    public void handle(ActionEvent event) {

        deletePost(postID);

    }

    private void deletePost(int postID) {
        if (postID < 0) {
            throw new AlertException("Invalid Post ID");
        }
        ListIterator<User> userIterator = Main.userList.iterator();
        //This is to find the post
        ListIterator<Post> postIterator = null;
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
            throw new AlertException("The post does not exists");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the post with id " + postID + " ?");

        if (alert.showAndWait().get() != ButtonType.OK)
            throw new AlertException("The post deleted cancelled");


        //To delete the post from the other users shared with
        for (User currUser : post.getSharedWith()) {
            currUser.getPostsSharedWith().delete(post);
        }

        //To delete it from the creator
        user.getPosts().delete(post);


    }
}
