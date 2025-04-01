package com.example.project2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FriendManagement extends BorderPane {

    public FriendManagement(Stage stage, User user) {

        stage.setTitle("Friend Management for "+user.getName()+" User");

        Button[] buttons = {new Button("Search for a friend by his name"),new Button("Add Friend by his ID"),
                new Button("Remove Friend by his ID"), new Button("Back")};

        TextField textField = new TextField();

        MainMenu.styling(buttons,40);

        textField.setFont(Font.font(30));
        textField.setPromptText("Enter Friend ID or Name");

        ObservableList<User> friendsList = FXCollections.observableArrayList();

        user.getFriends().addToObservableList(friendsList);

        TableView<User> tableView = new TableView<>(friendsList);

        TableColumn<User,Integer> IdTC = new TableColumn<>("Friend ID");
        IdTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        IdTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        IdTC.setMinWidth(300);

        TableColumn<User,String> NameTC = new TableColumn<>("Name");
        NameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        NameTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        NameTC.setMinWidth(400);

        TableColumn<User,Integer> ageTC = new TableColumn<>("Age");
        ageTC.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageTC.setStyle("-fx-font-size: 34px;-fx-alignment: CENTER;");
        ageTC.setMinWidth(300);

         tableView.getColumns().addAll(IdTC,NameTC,ageTC);

        setCenter(tableView);

        buttons[0].setTooltip(new Tooltip("Search for an empty text field to return to the normal table"));

        buttons[0].setOnAction(event -> {
            ObservableList<User> searchList = FXCollections.observableArrayList();
            String name = textField.getText();
            boolean cond = false;

            if (name.isEmpty()) {
                tableView.setItems(friendsList);
                return;
            }

            User prev = null;
            for (User friend : user.getFriends()) {
                if (friend.getName().toLowerCase().matches(".*"+textField.getText().toLowerCase()+".*")){
                    searchList.add(friend);
                    prev = friend;
                    cond = true;
                }else if (prev != null)
                    break;
            }
            if (!cond){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You don't have friends with this name");
                alert.showAndWait();
                return;
            }
            tableView.setItems(searchList);
        });

        buttons[1].setOnAction(e -> {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            try {
                int id = Integer.parseInt(textField.getText());
                User friend = user.addFriend(id);
                friendsList.add(friend);
                Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                informationAlert.setTitle("Success");
                informationAlert.setHeaderText(null);
                informationAlert.setContentText("Friend added successfully");
                informationAlert.showAndWait();
            }catch (NumberFormatException ex) {
                errorAlert.setContentText("Please enter a valid friend ID");
                errorAlert.showAndWait();
            }catch (AlertException ex1){
                errorAlert.setContentText(ex1.getMessage());
                errorAlert.showAndWait();
            }
        });

        buttons[2].setOnAction(e -> {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            try {
                int id = Integer.parseInt(textField.getText());
                new DeleteFriend(user.getId(),id,true).handle(null);
                friendsList.remove(Main.getUserFromID(id));
                Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                informationAlert.setTitle("Success");
                informationAlert.setHeaderText(null);
                informationAlert.setContentText("Friend removed successfully");
                informationAlert.showAndWait();
            }catch (NumberFormatException ex) {
                errorAlert.setContentText("Please enter a valid friend ID");
                errorAlert.showAndWait();
            }catch (AlertException ex1){
                errorAlert.setContentText(ex1.getMessage());
                errorAlert.showAndWait();
            }
        });

        buttons[3].setOnAction(e -> stage.setScene(new Scene(new UserMenu(stage,user))));

        HBox hBox = new HBox(30,buttons[0],buttons[1],buttons[2]);
        VBox vbox = new VBox(30, textField, hBox);

        setTop(vbox);

        setBottom(buttons[3]);
        BorderPane.setAlignment(buttons[3], Pos.BOTTOM_RIGHT);

    }
}
