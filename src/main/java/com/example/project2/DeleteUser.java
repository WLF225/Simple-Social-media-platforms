package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DeleteUser implements EventHandler<ActionEvent> {

    int id;


    public DeleteUser(int id) {
        this.id = id;

    }

    public void handle(ActionEvent event) {

        deleteUser(id);

    }

    private void deleteUser(int id) {

        User user = Main.getUserFromID(id);
        if (user == null) {
            throw new AlertException("The user with id " + id + " does not exists");
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the user with id " + id +
                " ?, this will delete all his posts.");

        if (alert.showAndWait().get() != ButtonType.OK) {
            throw new AlertException("The user delete cancelled");
        }


        //To remove the user from all the posts shared with him
        for (Post currPost : user.getPostsSharedWith()) {
            currPost.getSharedWith().delete(user);
        }

        //To remove the posts he created from shared with friends
        for (Post currPost : user.getPosts()) {
            for (User currFriend : currPost.getSharedWith()) {
                currFriend.getPostsSharedWith().delete(currPost);
            }
        }

        //To remove him from friends list to other users
        for (User currFriend : user.getFriends()) {
            currFriend.getFriends().delete(user);
        }

        //To remove him from the list
        Main.userList.delete(user);

    }
}
