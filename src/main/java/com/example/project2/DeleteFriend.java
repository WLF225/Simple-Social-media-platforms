package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.ListIterator;

public class DeleteFriend implements EventHandler<ActionEvent> {

    TextField tf;
    int userID;
    int friendID;
    boolean confirmation = true;

    //    public DeleteFriend(int userID, TextField tf) {
//        this.tf = tf;
//        this.userID = userID;
//    }
    public DeleteFriend(int userID, int friendID, boolean confirmation) {
        this.userID = userID;
        this.friendID = friendID;
        this.confirmation = confirmation;
    }

    public void handle(ActionEvent event) {

        deleteFriend(userID, friendID, confirmation);

    }

    private void deleteFriend(int userID, int friendID, boolean confirmation) {
        User user = Main.getUserFromID(userID);
        if (user == null)
            throw new AlertException("The user does not exists");

        User friend = user.getFriendFromId(friendID);

        if (friend == null)
            throw new AlertException("User " + user.getId() + " doesn't have friend with id " + friendID + ".");

        if (confirmation) {
            boolean ok = true;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete friend with id " + friendID + " ?");
            ok = alert.showAndWait().get() == ButtonType.OK;
            if (!ok)
                throw new AlertException("The friend delete canceled");
        }

        //To remove user id from shared to list in friend posts

        for (Post currPost:friend.getPosts()){
            currPost.getSharedWith().delete(user);
        }

        //To remove posts created by user from friend shared with list
        for (Post currPost:friend.getPostsSharedWith()){
            if (currPost.getCreatorID() == userID){
                friend.getPostsSharedWith().delete(currPost);
            }
        }

        //To remove friend id from shared to list in user posts
        for (Post currPost:user.getPosts()){
            currPost.getSharedWith().delete(user);
        }

        //To remove posts created by friend from user shared with list
        for (Post currPost:user.getPostsSharedWith()){
            if (currPost.getCreatorID() == userID){
                user.getPostsSharedWith().delete(currPost);
            }
        }

        //To delete them from each others friend list
        friend.getFriends().delete(user);
        user.getFriends().delete(friend);
    }

}
