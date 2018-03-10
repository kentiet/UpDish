package com.example.ken.updish.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tanthinh on 3/7/18.
 */

public class Post
{
    private String title;
    private String description;
    private int voteUp;
    private int voteDown;
    private ArrayList<String> positiveRate;
    private ArrayList<String> negativeRate;
    private ArrayList<Comment> commentList;
    private Location location;
    private Date datePost;
    private Calendar calendar = Calendar.getInstance();

    public Post(String title)
    {
        // Initializing
        int voteUp = 0;
        int voteDown = 0;
        description = "";
        datePost = calendar.getTime();
        positiveRate = new ArrayList<>();
        negativeRate = new ArrayList<>();
        commentList = new ArrayList<>();
        location = new Location();
    }
}
