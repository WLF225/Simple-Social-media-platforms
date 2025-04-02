package com.example.project2;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CreateNewPost extends Pane {

    public static int postID = 1;

    public CreateNewPost(Stage stage, User user) {

        stage.setTitle("Create New Post");

        ImageView imageView = new ImageView(new Image("createPost.png"));
        imageView.setLayoutX(1300);
        imageView.setLayoutY(100);

        Button[] buttons = {new Button("Back"),new Button("Clear"),new Button("Create")};

        MainMenu.styling(buttons,30);

        TextField[] textFields = {new TextField(), new TextField(), new TextField(), new TextField()};

        for (TextField textField : textFields) {
            textField.setFont(Font.font(25));
        }

        Label[] labels = {new Label("Post ID: "), new Label("Creator ID: "), new Label("Content: "),
                    new Label("Date: "),new Label("Shared with: ")};

        for (Label label : labels) {
            label.setFont(Font.font(30));
        }

        textFields[0].setDisable(true);
        textFields[1].setDisable(true);
        textFields[0].setText(postID+"");
        textFields[1].setText(user.getId()+"");

        //I wanted to make the date automatic when creating the post,
        //but the project say we need a datePicker
        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-font-size: 25;");

        RadioButton[] radioButtons = {new RadioButton("Share for specific friends"), new RadioButton("Share for all friends")};
        radioButtons[0].setSelected(true);
        radioButtons[0].setTooltip(new Tooltip("The format is 1,2,3"));

        ToggleGroup group = new ToggleGroup();
        for (RadioButton radioButton : radioButtons) {
            radioButton.setToggleGroup(group);
            radioButton.setFont(Font.font(20));
        }


        radioButtons[0].setOnAction(event -> textFields[3].setDisable(false));
        radioButtons[1].setOnAction(event -> {
            textFields[3].setDisable(true);
            textFields[3].setText("");
        });

        buttons[0].setOnAction(event -> stage.setScene(new Scene(new PostManagement(stage,user.getPosts(),user,true))));

        buttons[1].setOnAction(event -> {
            textFields[0].setText(postID+"");
            textFields[2].setText("");
            textFields[3].setText("");
            datePicker.setValue(null);
            radioButtons[0].fire();
        });

        buttons[2].setOnAction(event -> {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);

            if (datePicker.getValue() == null){
                errorAlert.setContentText("Please select a date.");
                errorAlert.showAndWait();
                return;
            }
            try {
                String sharedWith = "";
                if (radioButtons[1].isSelected()){
                    for (User friend : user.getFriends())
                        sharedWith += ","+ friend.getId();
                    if (!sharedWith.equals(""))
                        sharedWith = sharedWith.replaceFirst(",","");
                }else {
                    sharedWith = textFields[3].getText();
                }

                DLinkedList<Integer> sharedWithList = new DLinkedList<>();
                if (!sharedWith.isEmpty()) {
                    String[] sharedWithParts = sharedWith.split(",");

                    for (String sharedWithPart : sharedWithParts) {
                        sharedWithList.insetSorted(Integer.parseInt(sharedWithPart));
                    }
                }
                new Post(Integer.parseInt(textFields[0].getText()),Integer.parseInt(textFields[1].getText()),
                        textFields[2].getText(),datePicker.getValue().toString(),sharedWithList);

                postID++;

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Post created successfully.");
                successAlert.showAndWait();

                buttons[1].fire();

            }catch (AlertException e){
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
//            catch (NumberFormatException e1){
//                errorAlert.setContentText("Please enter a valid shared with format");
//                errorAlert.showAndWait();
//            }

        });


        VBox labelsVB = new VBox(40,labels[0],labels[1],labels[2],labels[3],labels[4]);

        VBox textFieldsVB = new VBox(30,textFields[0],textFields[1],textFields[2],datePicker,textFields[3]);

        HBox mainHBox = new HBox(30,labelsVB,textFieldsVB);

        HBox radioButtonsHB = new HBox(30,radioButtons[0],radioButtons[1]);

        VBox mainVB = new VBox(30,mainHBox, radioButtonsHB);
        mainVB.setLayoutX(100);
        mainVB.setLayoutY(200);



        HBox buttonsHB = new HBox(30,buttons);
        buttonsHB.setLayoutX(1300);
        buttonsHB.setLayoutY(900);

        getChildren().addAll(mainVB,buttonsHB,imageView);

    }
}
