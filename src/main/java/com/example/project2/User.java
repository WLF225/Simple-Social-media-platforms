package com.example.project2;

import java.util.ListIterator;

public class User implements Comparable<User> {

    private int id;
    private String name;
    private int age;
    private DLinkedList<User> friends;
    private DLinkedList<Post> posts;
    private DLinkedList<Post> postsSharedWith;


    public User(int id, String name, int age) {
        setId(id);
        setName(name);
        setAge(age);
        friends = new DLinkedList<>();
        posts = new DLinkedList<>();
        postsSharedWith = new DLinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0)
            throw new AlertException("ID cannot be negative.");
        duplicatedID(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0)
            throw new AlertException("Age cannot be negative.");
        if (age < 18)
            throw new AlertException("The user's age cannot be less than 18.");
        //The oldest human age is 116
        if (age > 116)
            throw new AlertException("The user's age cannot be more than 116.");
        this.age = age;
    }

    public DLinkedList<User> getFriends() {
        return friends;
    }

    public void setFriends(DLinkedList<User> friends) {
        this.friends = friends;
    }

    public DLinkedList<Post> getPosts() {
        return posts;
    }

    public void setPosts(DLinkedList<Post> posts) {
        this.posts = posts;
    }

    public DLinkedList<Post> getPostsSharedWith() {
        return postsSharedWith;
    }

    public void setPostsSharedWith(DLinkedList<Post> postsSharedWith) {
        this.postsSharedWith = postsSharedWith;
    }

    //To make sure the id does not exist
    public void duplicatedID(int id) {
        ListIterator<User> iterator = Main.userList.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().id == id)
                throw new AlertException("This ID already exists.");
        }
    }

    //To sort the users according to their name
    @Override
    public int compareTo(User o) {
        return name.compareTo(o.name);
    }


    public User addFriend(int id) {
        if (id == this.id)
            throw new AlertException("The user cannot add himself.");

        User friend = Main.getUserFromID(id);
        if (friend == null)
            throw new AlertException("The user with id "+id+" does not exist.");
        User exists = getFriendFromId(id);
        if (exists != null)
            throw new AlertException("The friend with id "+id+" already added.");
        friends.insetSorted(friend);
        friend.getFriends().insetSorted(this);
        return friend;
    }

    public User getFriendFromId(int id) {
        if (friends.isEmpty())
            return null;
        ListIterator<User> it = friends.iterator();
        while (it.hasNext()) {
            User user = it.next();
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return id + "," + name + "," + age;
    }

    public String printFriends(){
        String friendsString = id+"";
        ListIterator<User> curr = friends.iterator();
        while (curr.hasNext()) {
            friendsString += ","+curr.next().getId();
        }
        return friendsString;
    }

    public int postsLast3Weeks(){
        int postsNum = 0;
        for (Post post:posts) {                                                 //3 weeks millis
            if (System.currentTimeMillis() - post.getDate().getTimeInMillis() < 3*7*24*60*60*1000) {
                postsNum++;
            }
        }
        return postsNum;
    }
    public String friendsToString(){
        String friendsString = "";
        for (User user:friends) {
            friendsString += ", "+user.getName();
        }
        return friendsString.replaceFirst(",","");
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            return id == ((User) o).id;
        }
        return false;
    }
}
