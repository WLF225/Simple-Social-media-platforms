package com.example.project2;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ListIterator;

public class ViewFriends extends Pane {

    User friend = null;

    public ViewFriends(Stage stage, User user, int id){

        stage.setTitle("View friends");

        ListIterator<User> currFriend = user.getFriends().iterator();

        Label[] labels = {new Label("ID: "),new Label("Username: "),new Label("Age: ")};

        TextField[] textFields = {new TextField(),new TextField(),new TextField()};

        Button[] buttons = {new Button("Delete"),new Button("Previse"), new Button("Next"), new Button("Back")};

        ImageView imageView = new ImageView(new Image("friends.png"));

        //Styling
        for (Label label : labels) {
            label.setFont(Font.font(30));
        }
        for (TextField textField : textFields) {
            textField.setFont(Font.font(25));
            textField.setDisable(true);
        }

        imageView.setLayoutX(1100);
        imageView.setLayoutY(100);

        MainMenu.styling(buttons,40);

        buttons[0].setOnAction(e -> {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            try {
                int friendID = Integer.parseInt(textFields[0].getText());
                new DeleteFriend(user.getId(),friendID,true).handle(null);

                Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                informationAlert.setTitle("Success");
                informationAlert.setHeaderText(null);
                informationAlert.setContentText("Friend removed successfully");
                informationAlert.showAndWait();

                buttons[3].fire();
            }catch (AlertException ex1){
                errorAlert.setContentText(ex1.getMessage());
                errorAlert.showAndWait();
            }
        });

        buttons[1].setOnAction(event -> {
            friend = currFriend.previous();
            if (buttons[2].isDisable())
                buttons[2].setDisable(false);
            if(!currFriend.hasPrevious())
                buttons[1].setDisable(true);
            textFields[0].setText(friend.getId()+"");
            textFields[1].setText(friend.getName());
            textFields[2].setText(friend.getAge()+"");
        });

        buttons[2].setOnAction(event -> {
            friend = currFriend.next();
            if (buttons[1].isDisable())
                buttons[1].setDisable(false);
            if(!currFriend.hasNext())
                buttons[2].setDisable(true);
            textFields[0].setText(friend.getId()+"");
            textFields[1].setText(friend.getName());
            textFields[2].setText(friend.getAge()+"");
        });

        buttons[3].setOnAction(event -> {
            stage.setScene(new Scene(new FriendManagement(stage, user)));
        });

        //To find the friend with his iterator so we can use next and previse work
        while(currFriend.hasNext()){
            buttons[2].fire();
            if(friend.getId() == id){
                if (!currFriend.hasPrevious())
                    buttons[1].setDisable(true);
                break;
            }
        }

        //Setting the right position
        VBox labelsVB = new VBox(30,labels);
        VBox textFieldVB = new VBox(30,textFields);
        HBox mainHB = new HBox(30,labelsVB,textFieldVB);
        mainHB.setLayoutX(100);
        mainHB.setLayoutY(150);

        HBox nextPrevHB = new HBox(50,buttons[1],buttons[2]);
        nextPrevHB.setLayoutX(100);
        nextPrevHB.setLayoutY(600);

        HBox buttonsHB = new HBox(30,buttons[0],buttons[3]);
        buttonsHB.setLayoutX(1100);
        buttonsHB.setLayoutY(900);

        getChildren().addAll(imageView,mainHB,buttonsHB,nextPrevHB);

    }

}
