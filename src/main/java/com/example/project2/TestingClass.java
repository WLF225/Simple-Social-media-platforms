package com.example.project2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;

public class TestingClass extends Application {

    public static void main(String[] args) {

    launch(args);
    }
    public void start(Stage primaryStage) throws Exception {

//        DLinkedList<Integer> list = new DLinkedList<>();
//        list.insetSorted(1);
//        list.insetSorted(2);
//        list.insetSorted(3);
//        list.insetSorted(2);
//        list.insetSorted(3);
//        list.insetSorted(1);
//        list.insetSorted(1);
//        list.insetSorted(1);
//        list.traverse();
//        list.removeDuplicates();
//        list.traverse();

        try {
            LocalDate date = LocalDate.now();
            System.out.println(date);
            Main.userList.insetSorted(new User(1,"Ibrahim",116));
            Main.userList.insetSorted(new User(2,"Ahmed",20));
            Main.userList.insetSorted(new User(3,"Khalid",20));
//            Main.userList.traverse();
            DLinkedList<Integer> list = new DLinkedList<>();
            list.insetSorted(1);
            list.insetSorted(1);
            list.insetSorted(3);

            DNode<User> user1 = Main.getUserFromID(2);
            user1.getData().addFriend(1);
//            user.getData().addFriend(2);
            user1.getData().addFriend(3);

//        System.out.println(Main.getUserFromID(1));
//
//            Main.postList.insetSorted(new Post(1,-1,"dsadas","123",list));
//            Main.postList.insetSorted(new Post(2,2,"dsadas","123",list));
//            Main.postList.insetSorted(new Post(3,1,"dsadas","123",list));

            new Post(2,2,"dsadas","2000.02.12",list);
            new Post(3,2,"dsadas","2020.03.12",list);
            new Post(4,2,"dsadas","2020.03.12",list);

        DNode<User> user = Main.userList.getHead().getNext();
            while (user != Main.userList.getHead()){
                user.getData().getPostsSharedWith().traverse();
                user = user.getNext();
            }
//
//        DNode<User> user2 = Main.userList.getHead().getNext().getNext();
//            user2.getData().getPosts().traverse();
//        user2 = user2.getNext();
//        user2.getData().getPosts().traverse();

        System.exit(0);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
