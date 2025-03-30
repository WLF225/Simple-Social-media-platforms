package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Iterator;

public class DeleteFriend implements EventHandler<ActionEvent> {

    TextField tf;
    int userID;
    int friendID;

    public DeleteFriend(int userID, TextField tf) {
        this.tf = tf;
        this.userID = userID;
    }
    public DeleteFriend(int userID, int friendID) {
        this.userID = userID;
        this.friendID = friendID;
    }

    public void handle(ActionEvent event) {
        if (tf != null)
            friendID = Integer.parseInt(tf.getText());

        deleteFriend(userID,friendID,true);

    }
    public static void deleteFriend(int userID, int friendID, boolean confirmation) {
        User user = Main.getUserFromID(userID);
        if (user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The user does not exists");
            alert.showAndWait();
            return;
        }
        User friend = user.getFriendFromId(friendID);
        if (friend == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("User "+user.getId() +" doesn't have friend with id "+friendID+".");
            alert.showAndWait();
            return;
        }
        boolean ok = true;
        if (confirmation) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete friend with id "+ friendID+" ?");
            ok = alert.showAndWait().get() == ButtonType.OK;
        }
        if (ok) {
            //To remove user id from shared to list in friend posts
            Iterator<Post> currPost = friend.getPosts().iterator();
            while (currPost.hasNext()) {
                currPost.next().getSharedTo().delete(userID);
            }
            //To remove posts created by user from friend shared with list
            currPost = friend.getPostsSharedWith().iterator();
            while (currPost.hasNext()) {
                Post post = currPost.next();
                if (post.getCreatorID() == userID) {
                    friend.getPostsSharedWith().delete(post);
                }
            }
            //To remove friend id from shared to list in user posts
            currPost = user.getPosts().iterator();
            while (currPost.hasNext()) {
                currPost.next().getSharedTo().delete(userID);
            }
            //To remove posts created by friend from user shared with list
            currPost = user.getPostsSharedWith().iterator();
            while (currPost.hasNext()) {
                Post post = currPost.next();
                if (post.getCreatorID() == friendID) {
                    user.getPostsSharedWith().delete(post);
                }
            }
            //To delete them from each others friend list
            friend.getFriends().delete(user);
            user.getFriends().delete(friend);
        }
    }
}
