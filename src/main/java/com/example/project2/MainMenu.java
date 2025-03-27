package com.example.project2;


import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Scanner;


public class MainMenu extends BorderPane {

    public MainMenu(Stage stage) {
        Button[] buttons = {new Button("Read all the files")};
        styling(buttons,30);

        buttons[0].setOnAction(e -> {
            //This try is to make sure the user read all the 3 files
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText("You need to read user file then friends file then posts file or you will get an error.");
                alert.showAndWait();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Users file");

                File userFile = fileChooser.showOpenDialog(null);
                if (userFile == null) {
                    throw new AlertException("Couldn't find any file.");
                }

                fileChooser.setTitle("Open Friends file");
                File friendsFile = fileChooser.showOpenDialog(null);
                if (friendsFile == null) {
                    throw new AlertException("Friends file and posts file not found.");
                }

                fileChooser.setTitle("Open Posts file");
                File postsFile = fileChooser.showOpenDialog(null);
                if (postsFile == null) {
                    throw new AlertException("Posts file not found.");
                }

                //To read user file
                Scanner scanner = new Scanner(userFile);
                boolean cond = true;
                int lineNum = 1;
                while (scanner.hasNextLine()) {
                    try {
                        String line = scanner.nextLine();
                        String[] parts = line.split(",");
                        if (parts.length != 3) {
                            throw new NumberFormatException();
                        }
                        Main.userList.insetSorted(new User(Integer.parseInt(parts[0]),parts[1],Integer.parseInt(parts[2])));
                    }catch (AlertException e1){
                        if (cond) {
                            Alert alertE = new Alert(Alert.AlertType.ERROR);
                            //To close all the alerts at once
                            ButtonType closeAllBT = new ButtonType("Close all");
                            alertE.getButtonTypes().add(closeAllBT);
                            alertE.setTitle("Error");
                            alertE.setHeaderText(null);
                            alertE.setContentText(e1.getMessage()+"This error is in line "+lineNum+" in user file.");
                            if (alertE.showAndWait().get() == closeAllBT) {
                                cond = false;
                            }
                        }
                    }catch (NumberFormatException e2){
                        if (cond) {
                            Alert alertE = new Alert(Alert.AlertType.ERROR);
                            //To close all the alerts at once
                            ButtonType closeAllBT = new ButtonType("Close all");
                            alertE.getButtonTypes().add(closeAllBT);
                            alertE.setTitle("Error");
                            alertE.setHeaderText(null);
                            alertE.setContentText("Wrong user format in line "+lineNum+".");
                            if (alertE.showAndWait().get() == closeAllBT) {
                                cond = false;
                            }
                        }
                    }
                    lineNum++;
                }

                //To read the friends file
                scanner = new Scanner(friendsFile);
                lineNum = 1;
                while (scanner.hasNextLine()) {
                    try {
                        String line = scanner.nextLine();
                        String[] parts = line.split(",");
                        DNode<User> user = Main.getUserFromID(Integer.parseInt(parts[0]));
                        for (int i = 1; i < parts.length; i++) {
                            //This try is to make it read the next friend even if there is wrong friend in the line
                            try {
                                if (user == null)
                                    throw new AlertException("User you want to add friends for does not exist.");
                                user.getData().addFriend(Integer.parseInt(parts[i]));
                            } catch (AlertException e1) {
                                if (cond) {
                                    Alert alertE = new Alert(Alert.AlertType.ERROR);
                                    //To close all the alerts at once
                                    ButtonType closeAllBT = new ButtonType("Close all");
                                    alertE.getButtonTypes().add(closeAllBT);
                                    alertE.setTitle("Error");
                                    alertE.setHeaderText(null);
                                    alertE.setContentText(e1.getMessage() + "This error is in line " + lineNum + " in friend file.");
                                    if (alertE.showAndWait().get() == closeAllBT) {
                                        cond = false;
                                    }
                                    if (e1.getMessage().equals("User you want to add friends for does not exist."))
                                        break;
                                }
                            } catch (NumberFormatException e2) {
                                if (cond) {
                                    Alert alertE = new Alert(Alert.AlertType.ERROR);
                                    //To close all the alerts at once
                                    ButtonType closeAllBT = new ButtonType("Close all");
                                    alertE.getButtonTypes().add(closeAllBT);
                                    alertE.setTitle("Error");
                                    alertE.setHeaderText(null);
                                    alertE.setContentText("Wrong friend format in line " + lineNum + ".");
                                    if (alertE.showAndWait().get() == closeAllBT) {
                                        cond = false;
                                    }
                                }
                            }
                        }
                    } catch (NumberFormatException e3) {
                        if (cond) {
                            Alert alertE = new Alert(Alert.AlertType.ERROR);
                            //To close all the alerts at once
                            ButtonType closeAllBT = new ButtonType("Close all");
                            alertE.getButtonTypes().add(closeAllBT);
                            alertE.setTitle("Error");
                            alertE.setHeaderText(null);
                            alertE.setContentText("Wrong friend format in line " + lineNum + ".");
                            if (alertE.showAndWait().get() == closeAllBT) {
                                cond = false;
                            }
                        }
                    }
                    lineNum++;
                }
                //To read the post file
                scanner = new Scanner(postsFile);
                lineNum = 1;
                while (scanner.hasNextLine()) {
                    try {
                        String line = scanner.nextLine();
                        String[] parts = line.split(",");
                        DLinkedList<Integer> sharedWithLL = new DLinkedList<>();

                        for (int i = 4; i < parts.length; i++) {
                            sharedWithLL.insetSorted(Integer.parseInt(parts[i]));
                        }
                        new Post(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),parts[2],parts[3],sharedWithLL);
                    } catch (AlertException e1){
                        if (cond) {
                            Alert alertE = new Alert(Alert.AlertType.ERROR);
                            //To close all the alerts at once
                            ButtonType closeAllBT = new ButtonType("Close all");
                            alertE.getButtonTypes().add(closeAllBT);
                            alertE.setTitle("Error");
                            alertE.setHeaderText(null);
                            alertE.setContentText(e1.getMessage()+"This error is in line "+lineNum+" in post file.");
                            if (alertE.showAndWait().get() == closeAllBT) {
                                cond = false;
                            }
                        }
                    }catch (NumberFormatException e3) {
                        if (cond) {
                            Alert alertE = new Alert(Alert.AlertType.ERROR);
                            //To close all the alerts at once
                            ButtonType closeAllBT = new ButtonType("Close all");
                            alertE.getButtonTypes().add(closeAllBT);
                            alertE.setTitle("Error");
                            alertE.setHeaderText(null);
                            alertE.setContentText("Wrong post format in line " + lineNum + ".");
                            if (alertE.showAndWait().get() == closeAllBT) {
                                cond = false;
                            }
                        }
                    }
                    lineNum++;
                }

                DNode<User> user1 = Main.getUserFromID(1);
                user1.getData().getPostsSharedWith().traverse();

            }catch (Exception ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });



        HBox hbox = new HBox(30,buttons);
        hbox.setAlignment(Pos.CENTER);
        setTop(hbox);

    }

    public static void styling(Button[] buttons, int textSize) {
        //To style the buttons
        for (Button button : buttons) {
            button.setStyle(
                    "-fx-background-color: #3498db; " + // Blue background
                            "-fx-text-fill: white; " +          // White text
                            "-fx-font-size: " + textSize + "px; " +           // Font size
                            "-fx-font-weight: bold; " +        // Bold text
                            "-fx-padding: 10px 20px; " +       // Padding
                            "-fx-border-radius: 0; " +         // Sharp corners
                            "-fx-background-radius: 0; " +     // Sharp background
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);" // Subtle shadow
            );

            button.setOnMouseEntered(e -> button.setStyle(
                    "-fx-background-color: #2980b9; " + // Darker blue on hover
                            "-fx-text-fill: white; " +
                            "-fx-font-size: " + textSize + "px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-border-radius: 0; " +
                            "-fx-background-radius: 0; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            ));

            // Revert to original style on exit
            button.setOnMouseExited(e -> button.setStyle(
                    "-fx-background-color: #3498db; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: " + textSize + "px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-border-radius: 0; " +
                            "-fx-background-radius: 0; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            ));

            // Pressed effect
            button.setOnMousePressed(e -> button.setStyle(
                    "-fx-background-color: #1c5980; " + // Even darker blue when pressed
                            "-fx-text-fill: white; " +
                            "-fx-font-size: " + textSize + "px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-border-radius: 0; " +
                            "-fx-background-radius: 0; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            ));

            // Revert to hover style on release
            button.setOnMouseReleased(e -> button.setStyle(
                    "-fx-background-color: #2980b9; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: " + textSize + "px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-border-radius: 0; " +
                            "-fx-background-radius: 0; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            ));
        }
    }
}
