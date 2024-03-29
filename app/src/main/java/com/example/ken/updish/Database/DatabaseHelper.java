/*
    This DatabaseHelper class is used to wrap method to access the Database class
 */

package com.example.ken.updish.Database;

import android.util.Log;

//import com.example.ken.updish.Interface.DatabaseInterface;
import com.example.ken.updish.Model.Feature;
import com.example.ken.updish.Model.Location;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.Model.User;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by tanthinh on 3/19/18.
 */

public class DatabaseHelper {

    /* Include CRUD operation */
    private static DatabaseHelper databaseHelper = null;
    private static Database database = new Database();

    private DatabaseHelper(){} // Never instantiate

    public static Database getDatabase() {
        return database;

    }

    public static DatabaseHelper getInstance()
    {
        if(databaseHelper == null)
        {
            databaseHelper = new DatabaseHelper();
        }
        return databaseHelper;
    }

    // Return postList
    public ArrayList<Post> getPostList()
    {
        return database.getPostList();
    }

    /* GET */

    // Get current details post
    public Post getCurrentDetailsPost()
    {
        return database.getCurrentDetailsPost();
    }

    // Return Post at specific index
    public Post getPostAtPosition(int index)
    {
        return database.getPostList().get(index);
    }

    // Return post by post id
    public Post getPostById(int id)
    {
        Post returnPost = null;
        for(int i = 0; i < database.getPostList().size(); i++)
        {
            if(database.getPostList().get(i).getId() == id)
            {
                returnPost = database.getPostList().get(i);
                break;
            }
        }

        return returnPost;
    }

    // Return full address
    public String getCurrentPostFullAddress()
    {
        Location tempLoc = this.database.getCurrentDetailsPost().getLocation();
        String output = tempLoc.getAddress();
        return output;
    }

    public LatLng getLatlng() {
        Location tempLoc = this.database.getCurrentDetailsPost().getLocation();
        Log.e("dbhelpder", tempLoc.getLatitude() + " " +  tempLoc.getLongtitude());
        return  new LatLng(tempLoc.getLatitude(), tempLoc.getLongtitude());
    }


    public User getCurrentUser()
    {
        return database.getCurrentUser();
    }

    /* SET */

    // Set current details post
    public void setCurrentDetailsPost(Post newPost)
    {
        database.setCurrentDetailsPost(newPost);
    }

    // Set new postList
    public void setNewPostList(ArrayList<Post> newList) {
        database.setPostList(newList);
    }

    public void setCurrentUser(String name, String email, String[] plist)
    {
        User u = new User(name, email, plist);
        this.database.setCurrentUser(u);
    }

    public void setNewFeatureList(ArrayList<Feature> nList) {
        database.setFeatureList(nList);
    }

    public ArrayList<Feature> getFeatureList(){return this.database.getFeatureList();}
}
