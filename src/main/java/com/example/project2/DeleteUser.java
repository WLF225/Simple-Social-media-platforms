package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.ListIterator;

public class DeleteUser implements EventHandler<ActionEvent> {

    int id;
    boolean confirmation;

    public DeleteUser(int id,boolean confirmation) {
        this.id = id;
        this.confirmation = confirmation;
    }

    public void handle(ActionEvent event) {

        deleteUser(id,confirmation);

    }

    private void deleteUser(int id, boolean confirmation) {

        User user = Main.getUserFromID(id);
        if (user == null) {
            throw new AlertException("The user with id "+id+" does not exists");
        }

        if (confirmation) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the user with id "+id+
                    " ?, this will delete all his posts.");

            if(alert.showAndWait().get() != ButtonType.OK){
                throw new AlertException("The user delete cancelled");
            }
        }


            //To delete all his friends and posts from and to them

            ListIterator<User> iterator = user.getFriends().iterator();

            while (iterator.hasNext())
                new DeleteFriend(id,iterator.next().getId(),false).handle(null);

            //To delete the user from the list
            Main.userList.delete(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("User with id "+id+" deleted successfully");
            alert.showAndWait();



    }
}
