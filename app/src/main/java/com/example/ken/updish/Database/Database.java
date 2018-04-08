package com.example.ken.updish.Database;

import com.example.ken.updish.Interface.DatabaseInterface;
import com.example.ken.updish.Model.Feature;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.Model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by tanthinh on 3/19/18.
 */

public class Database
{
    private User currentUser;
    private Post currentDetailsPost;
    private ArrayList<Post> postList;
    private ArrayList<Feature> featureList;
    private ArrayList<Feature> newFeatureList;

    public Database()
    {
        featureList = new ArrayList<>();
        postList = new ArrayList<>();
        currentDetailsPost = null;
        featureList = new ArrayList<>();
        newFeatureList = new ArrayList<>();
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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Feature> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(ArrayList<Feature> featureList) {
        this.featureList = featureList;
    }

    public void setNewFeatureList(ArrayList<Feature> featureList) { this.newFeatureList = featureList; }

    public ArrayList<Feature> getNewFeatureList() {return newFeatureList; }
}
