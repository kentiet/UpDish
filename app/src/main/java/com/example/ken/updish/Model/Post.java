package com.example.ken.updish.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tanthinh on 3/7/18.
 */

public class Post
{
    private String id;
    private String title;
    private String description;
    private int voteUp;
    private int voteDown;
    private ArrayList<String> positiveRate;
    private ArrayList<String> negativeRate;
    private ArrayList<Comment> commentList;
    private Location location;
    private Date datePost;
    private User user;
    private ArrayList<Bitmap> imageList;
    private Calendar calendar = Calendar.getInstance();


    public Post(){
        this.id = "";
        int voteUp = 0;
        int voteDown = 0;
        this.imageList = new ArrayList<>();
        this.description = "";
        datePost = calendar.getTime();
        positiveRate = new ArrayList<>();
        negativeRate = new ArrayList<>();
        commentList = new ArrayList<>();
        location = new Location();
        this.user = new User("","",new String[10]);
    }

    public Post(String id, String title, User user, String description)
    {
        // Initializing
        this.id = id;
        int voteUp = 0;
        int voteDown = 0;
        imageList = new ArrayList<>();
        this.description = description;
        datePost = calendar.getTime();
        positiveRate = new ArrayList<>();
        negativeRate = new ArrayList<>();
        commentList = new ArrayList<>();
        location = new Location();
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVoteUp() {
        return voteUp;
    }

    public void setVoteUp(int voteUp) {
        this.voteUp = voteUp;
    }

    public int getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(int voteDown) {
        this.voteDown = voteDown;
    }

    public ArrayList<String> getPositiveRate() {
        return positiveRate;
    }

    public void setPositiveRate(ArrayList<String> positiveRate) {
        this.positiveRate = positiveRate;
    }

    public ArrayList<String> getNegativeRate() {
        return negativeRate;
    }

    public void setNegativeRate(ArrayList<String> negativeRate) {
        this.negativeRate = negativeRate;
    }

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Bitmap> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<Bitmap> imageList) {
        this.imageList = imageList;
    }
}
