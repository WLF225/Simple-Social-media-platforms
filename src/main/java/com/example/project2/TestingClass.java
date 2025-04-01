package com.example.project2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ListIterator;


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

//        try {

        Main.userList.insetSorted(new User(1, "Ibrahim", 116));
        Main.userList.insetSorted(new User(2, "Ahmed", 20));
        Main.userList.insetSorted(new User(3, "Khalid", 20));
//            Main.userList.traverse();
        DLinkedList<Integer> list = new DLinkedList<>();
        list.insetSorted(1);
        list.insetSorted(1);
        list.insetSorted(3);
//
//            System.out.println(Main.getUserFromID(1));
//            System.out.println(Main.getUserFromID(2));
//            System.out.println(Main.getUserFromID(3));
//
//            Main.userList.delete(Main.getUserFromID(3).getData());
//
//            System.out.println(Main.getUserFromID(1));
//            System.out.println(Main.getUserFromID(2));
//            System.out.println(Main.getUserFromID(3));

        User user1 = Main.getUserFromID(2);
        user1.addFriend(1);
//            user.getData().addFriend(2);
        user1.addFriend(3);

//        System.out.println(Main.getUserFromID(1));
//
//            Main.postList.insetSorted(new Post(1,-1,"dsadas","123",list));
//            Main.postList.insetSorted(new Post(2,2,"dsadas","123",list));
//            Main.postList.insetSorted(new Post(3,1,"dsadas","123",list));

        Post post1 = new Post(2, 2, "dsadas", "12-1-2000", list);
        new Post(3, 2, "dsadas", "12.03.2020", list);
        new Post(4, 2, "dsadas", "12.4.2020", list);


//        User user3 = Main.getUserFromID(2);

        ListIterator<User> iterator = Main.userList.iterator();
//        while (iterator.hasPrevious()) {
//            System.out.println(iterator.previous().print());
//        }
//
//        while (listIterator1.hasNext()) {
//            System.out.println(((Post) listIterator1.prev()).print());
//        }
//
//        for (User user:Main.userList) {
//            System.out.println(user.print());
//            user.getPostsSharedWith().traverse();
//        }
//
//        DeleteUser.deleteUser(2,false);
//
//        iterator = Main.userList.iterator();
//
//        while (iterator.hasNext()) {
//            User user = iterator.next();
//            System.out.println(user.print());
//            user.getPostsSharedWith().traverse();
//        }

//        DNode<User> user = Main.userList.getHead().getNext();
//            while (user != Main.userList.getHead()){
//                user.getData().getPostsSharedWith().traverse();
//                user = user.getNext();
//            }


//        user = Main.getUserFromID(2);
//            user.getData().getPosts().getHead().getNext().getData().setContent("HOHOHO");
//
//            user = Main.userList.getHead().getNext();
//            while (user != Main.userList.getHead()){
//                user.getData().getPostsSharedWith().traverse();
//                user = user.getNext();
//            }
//            DNode<User> user2 = Main.getUserFromID(2);
//            user2.getData().getPosts().getHead().getNext().getData().getDate();
//            System.out.println(user2.getData().getPosts().getHead().getNext().getData().getDate());

//
//        DNode<User> user2 = Main.userList.getHead().getNext().getNext();
//            user2.getData().getPosts().traverse();
//        user2 = user2.getNext();
//        user2.getData().getPosts().traverse();

        System.exit(0);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
    }
}
