package com.example.project2;

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
        DLinkedList<User> list = Main.userList;
        DNode<User> curr = list.getHead().getNext();

        while (curr != list.getHead() && curr.getData().getId() <= id) {
            if (curr.getData().id == id)
                throw new AlertException("This ID already exists.");
            curr = curr.getNext();
        }
    }

    //To sort the users according to their id
    @Override
    public int compareTo(User o) {
        return id - o.id;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age;
    }

    public void addFriend(int id) {
        if (id == this.id)
            throw new AlertException("The user cannot add himself.");

        DNode<User> friend = Main.getUserFromID(id);
        if (friend == null)
            throw new AlertException("The user with id "+id+" does not exist.");
        DNode<User> exists = getFriendFromId(id);
        if (exists != null)
            throw new AlertException("The friend with id "+id+" already added.");
        friends.insetSorted(friend.getData());
        friend.getData().getFriends().insetSorted(this);
    }

    public DNode<User> getFriendFromId(int id) {
        if (friends.isEmpty())
            return null;
        DNode<User> curr = friends.getHead().getNext();
        while (curr != friends.getHead()){
            if (curr.getData().getId() == id)
                return curr;
            curr = curr.getNext();
        }
        return null;
    }

    public String print(){
        return id + "," + name + "," + age;
    }

    public String printFriends(){
        String friendsString = id+"";
        DNode<User> curr = friends.getHead().getNext();
        while (curr != friends.getHead()) {
            friendsString += ","+curr.getData().getId();
            curr = curr.getNext();
        }
        return friendsString;
    }
}
