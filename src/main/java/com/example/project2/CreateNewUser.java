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

public class CreateNewUser extends Pane {

    public static int userID = 1;

    public CreateNewUser(Stage stage) {

        stage.setTitle("Create New User");

        Label[] labels = {new Label("ID: "),new Label("Username: "),new Label("Age: ")};

        TextField[] textFields = {new TextField(),new TextField(),new TextField()};
        textFields[0].setText(userID+"");
        textFields[0].setDisable(true);

        ImageView imageView = new ImageView(new Image("addUser.png"));


        Button[] buttons = {new Button("Back"),new Button("Clear"),new Button("Create")};

        //Styling
        for (Label label : labels) {
            label.setFont(Font.font(30));
        }
        for (TextField textField : textFields) {
            textField.setFont(Font.font(25));
        }

        MainMenu.styling(buttons,40);

        //Setting the right position
        VBox labelsVB = new VBox(30,labels);
        VBox textFieldVB = new VBox(30,textFields);
        HBox mainHB = new HBox(30,labelsVB,textFieldVB);
        mainHB.setLayoutX(100);
        mainHB.setLayoutY(150);

        HBox buttonsHB = new HBox(30,buttons);
        buttonsHB.setLayoutX(1100);
        buttonsHB.setLayoutY(900);

        imageView.setLayoutX(1100);
        imageView.setLayoutY(100);

        //Buttons code
        buttons[0].setOnAction(event -> stage.setScene(new Scene(new MainMenu(stage))));

        buttons[1].setOnAction(event -> {
            textFields[0].setText(userID+"");
            textFields[1].setText("");
            textFields[2].setText("");
        });

        buttons[2].setOnAction(event -> {

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);

            try {
                if (textFields[1].getText().isEmpty()){
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("The username cannot be empty.");
                    errorAlert.showAndWait();
                    return;
                }
                Main.userList.insetSorted(new User(userID,textFields[1].getText(),
                        Integer.parseInt(textFields[2].getText())));

                Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                informationAlert.setTitle("Success");
                informationAlert.setHeaderText(null);
                informationAlert.setContentText("User created successfully with ID: " + userID+", you will need the ID to loge in.");
                informationAlert.showAndWait();

                userID++;
                buttons[1].fire();

                buttons[0].fire();
            }catch (AlertException e){
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }catch (NumberFormatException e2){
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Age text field should be an integer");
                errorAlert.showAndWait();
            }
        });

        getChildren().addAll(mainHB,imageView,buttonsHB);
    }
}
