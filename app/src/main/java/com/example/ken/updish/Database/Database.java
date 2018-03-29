package com.example.ken.updish.Database;

import com.example.ken.updish.Interface.DatabaseInterface;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.Model.User;

import java.util.ArrayList;

/**
 * Created by tanthinh on 3/19/18.
 */

public class Database
{
    private Post currentDetailsPost;
    private ArrayList<Post> postList;

    public Database()
    {
        postList = new ArrayList<>();
        currentDetailsPost = null;
    }

    public Post getCurrentDetailsPost() {
        return currentDetailsPost;
    }

    public void setCurrentDetailsPost(Post currentDetailsPost) {
        this.currentDetailsPost = currentDetailsPost;
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public void setPostList(ArrayList<Post> postList) {
        this.postList = postList;
    }
}
