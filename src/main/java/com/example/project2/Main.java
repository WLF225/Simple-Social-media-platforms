package com.example.project2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ListIterator;

public class Main extends Application {

    public static DLinkedList<User> userList = new DLinkedList<>();
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new MainMenu(stage));
        stage.setTitle("Main Menu");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static User getUserFromID(int id) {
        ListIterator<User> it = userList.iterator();
        while (it.hasNext()) {
            User user = it.next();
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}