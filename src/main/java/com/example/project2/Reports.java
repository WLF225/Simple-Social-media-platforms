package com.example.project2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;

public class Reports extends Pane {

    public Reports(Stage stage) {

        Button[] buttons = {new Button("Print post created report in posts_created.txt"),
                new Button("Print posts shared in posts_shared.txt"), new Button("Print friends in friends_report.txt"),
                new Button("Back")};

        MainMenu.styling(buttons, 30);

        ImageView imageView = new ImageView(new Image("statReports.png"));

        imageView.setLayoutX(1250);
        imageView.setLayoutY(100);

        buttons[0].setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("How do you want the report to be sorted based on username ?");
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Ascending", "Descending");
            comboBox.getSelectionModel().selectFirst();
            alert.getDialogPane().setContent(comboBox);
            if (alert.showAndWait().get() == ButtonType.OK) {
                try (PrintWriter pw = new PrintWriter("posts_created.txt")) {
                    pw.println("Posts Created Report");
                    pw.println("------------------------");
                    if (comboBox.getValue().equals("Ascending")) {
                        for (User currUser : Main.userList) {
                            pw.println("User: " + currUser.getName());
                            for (Post post : currUser.getPosts()) {
                                pw.println(post.postCreatedPrint());
                            }
                        }
                    } else {
                        ListIterator<User> userIterator = Main.userList.iterator();
                        while (userIterator.hasPrevious()) {
                            User currUser = userIterator.previous();
                            pw.println("User: " + currUser.getName());
                            for (Post post : currUser.getPosts()) {
                                pw.println(post.postCreatedPrint());
                            }
                        }
                    }
                } catch (IOException e1) {

                }
            }
        });

        buttons[1].setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("How do you want the report to be sorted based on username ?");
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Ascending", "Descending");
            comboBox.getSelectionModel().selectFirst();
            alert.getDialogPane().setContent(comboBox);
            if (alert.showAndWait().get() == ButtonType.OK) {
                try (PrintWriter pw = new PrintWriter("posts_shared.txt")) {
                    pw.println("Posts Shared Report");
                    pw.println("------------------------");
                    if (comboBox.getValue().equals("Ascending")) {
                        for (User currUser : Main.userList) {
                            pw.println("User: " + currUser.getName());
                            for (Post post : currUser.getPostsSharedWith()) {
                                pw.println(post.postSharedWithPrint());
                            }
                        }
                    } else {
                        ListIterator<User> userIterator = Main.userList.iterator();
                        while (userIterator.hasPrevious()) {
                            User currUser = userIterator.previous();
                            pw.println("User: " + currUser.getName());
                            for (Post post : currUser.getPostsSharedWith()) {
                                pw.println(post.postSharedWithPrint());
                            }
                        }
                    }
                } catch (IOException e1) {

                }
            }
        });

        buttons[2].setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("How do you want the report to be sorted based on username ?");
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Ascending", "Descending");
            comboBox.getSelectionModel().selectFirst();
            alert.getDialogPane().setContent(comboBox);
            if (alert.showAndWait().get() == ButtonType.OK) {
                try (PrintWriter pw = new PrintWriter("friends_report.txt")) {
                    pw.println("Friends Report");
                    pw.println("------------------------");
                    if (comboBox.getValue().equals("Ascending")) {
                        for (User currUser : Main.userList) {
                            pw.println("User: " + currUser.getName() + ", his friends: " + currUser.friendsToString());
                        }
                    } else {
                        ListIterator<User> userIterator = Main.userList.iterator();
                        while (userIterator.hasPrevious()) {
                            User currUser = userIterator.previous();
                            pw.println("User: " + currUser.getName() + ", his friends: " + currUser.friendsToString());
                        }
                    }

                } catch (IOException e1) {
                }
            }
        });
        buttons[3].setOnAction(e -> stage.setScene(new Scene(new ReportingAndStatistics(stage))));
        buttons[3].setLayoutX(1450);
        buttons[3].setLayoutY(900);

        //To find the most active user/s
        DLinkedList<User> userWithMostPosts = new DLinkedList<>();
        int maxSize = 0;
        for (User currUser : Main.userList) {
            if (currUser.getPosts().size() > maxSize) {
                maxSize = currUser.getPosts().size();
                userWithMostPosts.clear();
                userWithMostPosts.insetSorted(currUser);
            } else if (currUser.getPosts().size() == maxSize)
                userWithMostPosts.insetSorted(currUser);
        }

        DLinkedList<User> userWithMostPostsLast3Weeks = new DLinkedList<>();
        int maxSize3Weeks = 0;
        for (User currUser : Main.userList) {
            if (currUser.postsLast3Weeks() > maxSize3Weeks) {
                maxSize3Weeks = currUser.getPosts().size();
                userWithMostPostsLast3Weeks.clear();
                userWithMostPostsLast3Weeks.insetSorted(currUser);
            } else if (currUser.postsLast3Weeks() == maxSize3Weeks)
                userWithMostPostsLast3Weeks.insetSorted(currUser);
        }

        TextArea mostActiveTA = new TextArea();
        mostActiveTA.setFont(Font.font(25));
        mostActiveTA.setEditable(false);
        mostActiveTA.appendText("User/s with most posts:\n");

        for (User currUser : userWithMostPosts) {
            mostActiveTA.appendText("User ID: " + currUser.getId() + ", User name: " + currUser.getName() + ", with " + maxSize + " posts\n");
        }
        mostActiveTA.appendText("\nUser/s with most posts last 3 weeks:\n");
        for (User currUser : userWithMostPostsLast3Weeks) {
            mostActiveTA.appendText("User ID: " + currUser.getId() + ", User name: " + currUser.getName() + ", with " + maxSize3Weeks + " posts\n");
        }

        mostActiveTA.setLayoutX(100);
        mostActiveTA.setLayoutY(350);

        mostActiveTA.setPrefHeight(300);

        TextArea usersPostsReport = new TextArea();
        usersPostsReport.setFont(Font.font(25));
        usersPostsReport.setEditable(false);
        usersPostsReport.appendText("Users posts count:\n");

        for (User currUser : Main.userList) {
            usersPostsReport.appendText(currUser.getName() + " has created " + currUser.getPosts().size() +
                    " post and has " + currUser.getPostsSharedWith().size() + " posts shared with him\n");
        }
        usersPostsReport.setLayoutX(100);
        usersPostsReport.setLayoutY(700);

        usersPostsReport.setPrefHeight(300);


        VBox vbox = new VBox(40, buttons[0], buttons[1], buttons[2]);
        vbox.setLayoutX(200);
        vbox.setLayoutY(50);
        vbox.setAlignment(Pos.CENTER);

        getChildren().addAll(vbox, buttons[3], mostActiveTA, usersPostsReport, imageView);
    }
}
