package com.example.ken.updish.Model;

import java.util.Date;

/**
 * Created by tanthinh on 3/7/18.
 */

public class Comment
{
    private int id;
    private String content;
    private Date date_comment;
    private User user;

    public Comment()
    {

    }

    /**
     * Comment constructor
     * @param content   content of Comment
     * @param date      date the Comment is posted
     * @param user      user who posts comment
     */
    public Comment(int id, String content, Date date, User user)
    {
        this.id = id;
        this.content = content;
        this.date_comment = date;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate_comment() {
        return date_comment;
    }

    public void setDate_comment(Date date_comment) {
        this.date_comment = date_comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
