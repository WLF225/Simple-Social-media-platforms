package com.example.project2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static DLinkedList<User> userList = new DLinkedList<>();
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new MainMenu(stage));
        stage.setTitle("Main Menu");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static DNode<User> getUserFromID(int id) {
        DNode<User> curr = userList.getHead().getNext();
        while (curr != userList.getHead() && id >= curr.getData().getId()){
            if (curr.getData().getId() == id)
                return curr;
            curr = curr.getNext();
        }
        return null;
    }
}