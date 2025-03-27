package com.example.project2;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Post implements Comparable<Post> {

    private int id;
    private int creatorID;
    private String content;
    private String date;
    private DLinkedList<Integer> sharedTo;

    public Post(int id, int creatorID, String content, String date, DLinkedList<Integer> sharedTo) {
        setId(id);
        setCreatorID(creatorID);
        setContent(content);
        setDate(date);
        setSharedTo(sharedTo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0)
            throw new AlertException("post ID cannot be negative.");
        duplicateID(id);
        this.id = id;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        if (creatorID < 0)
            throw new AlertException("post creatorID cannot be negative.");
        //To make sure the user with that id exists
        DNode<User> user = Main.getUserFromID(creatorID);
        if (user == null)
            throw new AlertException("This user does not exist.");
        //To add the post created to the user
        user.getData().getPosts().insetSorted(this);
        this.creatorID = creatorID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (date.matches("\\d{1,2}.\\d{1,2}.\\d{4}")) {

            String[] dateParts = date.split("\\.");
            int[] dateInt = new int[3];
            for (int i = 0; i < dateParts.length; i++) {
                dateInt[i] = Integer.parseInt(dateParts[i]);
            }
            GregorianCalendar gc = new GregorianCalendar(dateInt[2], dateInt[1]-1, dateInt[0]);
            if (gc.get(Calendar.YEAR) < 2000)
                throw new AlertException("The program was invented in 2000.");
            if (System.currentTimeMillis() < gc.getTimeInMillis())
                throw new AlertException("You cant put future time in this program.");
            this.date = gc.get(Calendar.DAY_OF_MONTH)+"."+(gc.get(Calendar.MONTH)+1)+"."+gc.get(Calendar.YEAR);
        }else
            throw new AlertException("Wrong date format.");

    }

    public DLinkedList<Integer> getSharedTo() {
        return sharedTo;
    }

    public void setSharedTo(DLinkedList<Integer> sharedTo) {
        sharedTo.removeDuplicates();
        DNode<Integer> currInt = sharedTo.getHead().getNext();
        DNode<User> creator = Main.getUserFromID(creatorID);
        //To make sure the person he want  to share the post with is his friend
        while (currInt != sharedTo.getHead()) {
            DNode<User> friend = creator.getData().getFriendFromId(currInt.getData());
            if (friend == null)
                throw new AlertException("This user can only share to his friends.");
            friend.getData().getPostsSharedWith().insetSorted(this);
            currInt = currInt.getNext();
        }
        this.sharedTo = sharedTo;
    }
    //To make sure the id does not exist
    public void duplicateID(int id){
        DNode<User> curr = Main.userList.getHead().getNext();
        while (curr != Main.userList.getHead()) {
            DNode<Post> pL = curr.getData().getPosts().getHead().getNext();
            while (pL != curr.getData().getPosts().getHead()) {
                if (pL.getData().getId() == id) {
                    throw new AlertException("Duplicate post ID.");
                }
                pL = pL.getNext();
            }
            curr = curr.getNext();
        }
    }


    //To sort the post according to the id
    @Override
    public int compareTo(Post o) {
        return id - o.id;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", CreatorID: " + creatorID + ", Content: " + content + ", Date: " + date;
    }


}
