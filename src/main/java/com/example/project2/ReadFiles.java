package com.example.project2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.Scanner;

public class ReadFiles implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {
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
                    if (CreateNewUser.userID <= Integer.parseInt(parts[0]))
                        CreateNewUser.userID = Integer.parseInt(parts[0])+1;
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
                    User user = Main.getUserFromID(Integer.parseInt(parts[0]));
                    for (int i = 1; i < parts.length; i++) {
                        //This try is to make it read the next friend even if there is wrong friend in the line
                        try {
                            if (user == null)
                                throw new AlertException("User you want to add friends for does not exist.");
                            user.addFriend(Integer.parseInt(parts[i]));
                        } catch (AlertException e1) {
                            //To not get the friend is already added warning
                            if (e1.getMessage().equals("The friend with id "+parts[i]+" already added."))
                                continue;
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
            Alert finishAlert = new Alert(Alert.AlertType.INFORMATION);
            finishAlert.setTitle("Success");
            finishAlert.setHeaderText(null);
            finishAlert.setContentText("All the date has been read successfully.");
            finishAlert.showAndWait();

        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
