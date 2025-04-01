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

public class EditUser extends Pane {

    public EditUser(Stage stage, User user) {

        stage.setTitle("Edit "+user.getName()+ " User");

        Label[] labels = {new Label("ID: "),new Label("Username: "),new Label("Age: ")};

        TextField[] textFields = {new TextField(),new TextField(),new TextField()};
        textFields[0].setText(user.getId()+"");
        textFields[0].setDisable(true);

        ImageView imageView = new ImageView(new Image("EditUser.png"));


        Button[] buttons = {new Button("Back"),new Button("Refill"),new Button("Edit")};

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
        buttons[0].setOnAction(event -> {
            stage.setScene(new Scene(new UserMenu(stage,user)));
        });

        buttons[1].setOnAction(event -> {
            textFields[1].setText(user.getName());
            textFields[2].setText(user.getAge()+"");
        });

        buttons[2].setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            try {
                if (textFields[1].getText().isEmpty()) {
                    alert.setContentText("You must enter a user name");
                    alert.showAndWait();
                    return;
                }
                user.setName(textFields[1].getText());
                user.setAge(Integer.parseInt(textFields[2].getText()));

                stage.setTitle("Edit "+user.getName()+ " User");

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("User information updated successfully!");
                alert.showAndWait();

            }catch (AlertException e){
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }catch (NumberFormatException e1){
                alert.setContentText("Age must be an integer");
                alert.showAndWait();
            }
        });

        buttons[1].fire();

        getChildren().addAll(imageView,mainHB,buttonsHB);
    }
}
