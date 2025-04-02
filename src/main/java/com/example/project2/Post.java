package com.example.project2;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ListIterator;

public class Post implements Comparable<Post> {

    private int id;
    private int creatorID;
    private String content;
    private GregorianCalendar date;
    private DLinkedList<User> sharedWith;

    private String creatorName;

    public Post(int id, int creatorID, String content, String date, DLinkedList<Integer> sharedTo) {
        setId(id);
        setCreatorID(creatorID);
        setContent(content);
        setDate(date);
        setSharedWith(sharedTo);
        //To add the post created to the user
        User user = Main.getUserFromID(creatorID);
        user.getPosts().insetSorted(this);
        creatorName = user.getName();
        //To add it tho friends shared with
        ListIterator<Integer> iterator = sharedTo.iterator();
        while (iterator.hasNext()) {
            User friend = Main.getUserFromID(iterator.next());
            friend.getPostsSharedWith().insetSorted(this);
        }
    }


    public int getId() {
        return id;
    }

    private void setId(int id) {
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

    public String getCreatorName() {
        return creatorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        //I add this to make sure the user dont save any content with ,
        content = content.replaceAll(",", "،");
        this.content = content;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String dateToString() {
        return date.get(Calendar.DAY_OF_MONTH) + "." + (date.get(Calendar.MONTH) + 1) + "." + date.get(Calendar.YEAR);
    }

    public void setDate(String date) {
        String[] dateParts;
        if (date.matches("\\d{1,2}\\.\\d{1,2}\\.\\d{4}")) {
            dateParts = date.split("\\.");
        } else if (date.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
            dateParts = date.split("-");
        } else
            throw new AlertException("Wrong date format.");

        int[] dateInt = new int[3];
        for (int i = 0; i < dateParts.length; i++) {
            dateInt[i] = Integer.parseInt(dateParts[i]);
        }
        GregorianCalendar gc;
        if (dateParts[0].matches("\\d{1,2}"))
            gc = new GregorianCalendar(dateInt[2], dateInt[1] - 1, dateInt[0]);
        else
            gc = new GregorianCalendar(dateInt[0], dateInt[1]-1, dateInt[2]);
        if (gc.get(Calendar.YEAR) < 2000)
            throw new AlertException("The program was invented in 2000.");
        if (System.currentTimeMillis() < gc.getTimeInMillis())
            throw new AlertException("You cant put future time in this program.");
        this.date = gc;
    }



    public String sharedToToString() {
        String sharedToString = "";
        for (User user:sharedWith){
            sharedToString += "," + user.getId();
        }
        return sharedToString;
    }

    public void setSharedWith(DLinkedList<Integer> sharedTo) {
        sharedWith = new DLinkedList<>();
        sharedTo.removeDuplicates();
        User creator = Main.getUserFromID(creatorID);
        //To make sure the person he want to share the post with is his friend
        for (Integer currInt: sharedTo){
            User friend = creator.getFriendFromId(currInt);

            if (friend == null)
                throw new AlertException("This user can only share to his friends.");

            sharedWith.insetSorted(friend);
        }
    }

    public DLinkedList<User> getSharedWith() {
        return sharedWith;
    }

    //To make sure the id does not exist
    public void duplicateID(int id) {
        ListIterator<User> curr = Main.userList.iterator();
        while (curr.hasNext()) {
            User user = curr.next();
            ListIterator<Post> currPost = user.getPosts().iterator();
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
        return id + "," + creatorID + "," + content + ","
                + dateToString() + sharedToToString();
    }

    public String sharedWithNames(){
        String sharedWithNames = "";
        for (User user:sharedWith){
            sharedWithNames += ", "+user.getName();
        }
        return sharedWithNames.replaceFirst(",","");
    }

    public String postCreatedPrint(){
        return "- Post ID: "+id+", Content: "+content+", "+dateToString()+", Shared with: "
                +sharedWithNames();
    }

    public String postSharedWithPrint(){
        return "- Post ID: "+id+", Content: "+content+", "+dateToString()+", Creator: "
                +creatorName;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Post) {
            return id == ((Post) o).id;
        }
        return false;
    }


}
