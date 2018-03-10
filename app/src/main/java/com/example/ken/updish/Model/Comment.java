package com.example.ken.updish.Model;

import java.util.Date;

/**
 * Created by tanthinh on 3/7/18.
 */

public class Comment
{
    private String content;
    private Date date;
    private User user;

    /**
     * Comment constructor
     * @param content   content of Comment
     * @param date      date the Comment is posted
     * @param user      user who posts comment
     */
    public Comment(String content, Date date, User user)
    {
        this.content = content;
        this.date = date;
        this.user = user;
    }
}
