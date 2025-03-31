package com.example.project2;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.regex.Pattern;

public class Post implements Comparable<Post> {

    private int id;
    private int creatorID;
    private String content;
    private GregorianCalendar date;
    private DLinkedList<Integer> sharedTo;

    public Post(int id, int creatorID, String content, String date, DLinkedList<Integer> sharedTo) {
        setId(id);
        setCreatorID(creatorID);
        setContent(content);
        setDate(date);
        setSharedTo(sharedTo);
        //To add the post created to the user
        User user = Main.getUserFromID(creatorID);
        user.getPosts().insetSorted(this);
        //To add it tho friends shared with
        Iterator<Integer> iterator = sharedTo.iterator();
        while (iterator.hasNext()) {
            User friend = Main.getUserFromID(iterator.next());
            friend.getPostsSharedWith().insetSorted(this);
        }
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
        User user = Main.getUserFromID(creatorID);
        if (user == null)
            throw new AlertException("This user does not exist.");

        this.creatorID = creatorID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String dateToString() {
        return date.get(Calendar.DAY_OF_MONTH)+"."+ (date.get(Calendar.MONTH)+1)+"."+date.get(Calendar.YEAR);
    }

    public void setDate(String date) {
        String[] dateParts;
        if (date.matches("\\d{1,2}\\.\\d{1,2}\\.\\d{4}")) {
            dateParts = date.split("\\.");
        } else if (date.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
            dateParts = date.split("-");
        } else
            throw new AlertException("Wrong date format.");



        int[] dateInt = new int[3];
        for (int i = 0; i < dateParts.length; i++) {
            dateInt[i] = Integer.parseInt(dateParts[i]);
        }
        GregorianCalendar gc = new GregorianCalendar(dateInt[2], dateInt[1] - 1, dateInt[0]);
        if (gc.get(Calendar.YEAR) < 2000)
            throw new AlertException("The program was invented in 2000.");
        if (System.currentTimeMillis() < gc.getTimeInMillis())
            throw new AlertException("You cant put future time in this program.");
        this.date = gc;


    }

    public DLinkedList<Integer> getSharedTo() {
        return sharedTo;
    }

    public String sharedToToString() {
        Iterator<Integer> curr = sharedTo.iterator();
        String sharedToString = "";
        while (curr.hasNext()) {
            sharedToString += "," + curr.next();
        }
        return sharedToString;
    }

    public void setSharedTo(DLinkedList<Integer> sharedTo) {
        sharedTo.removeDuplicates();
        Iterator<Integer> currInt = sharedTo.iterator();
        User creator = Main.getUserFromID(creatorID);
        //To make sure the person he want  to share the post with is his friend
        while (currInt.hasNext()) {
            User friend = creator.getFriendFromId(currInt.next());
            if (friend == null)
                throw new AlertException("This user can only share to his friends.");
        }
        this.sharedTo = sharedTo;
    }

    //To make sure the id does not exist
    public void duplicateID(int id) {
        Iterator<User> curr = Main.userList.iterator();
        while (curr.hasNext()) {
            User user = curr.next();
            Iterator<Post> currPost = user.getPosts().iterator();
            while (currPost.hasNext()) {
                if (currPost.next().getId() == id) {
                    throw new AlertException("Duplicate post ID.");
                }
            }
        }
    }


    //To sort the posts making the newest first
    @Override
    public int compareTo(Post o) {
        return o.date.compareTo(this.date);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", CreatorID: " + creatorID + ", Content: " + content + ", Date: " + dateToString() + ", sharedTo: " + sharedToToString();
    }

    public String print() {
        return id + "," + creatorID + "," + content + ","
                + dateToString() + sharedToToString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Post) {
            return id == ((Post) o).id;
        }
        return false;
    }


}
