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
    private ArrayList<User> userList;
    private ArrayList<Post> postList;

    public Database()
    {
        userList = new ArrayList<>();
        postList = new ArrayList<>();
    }
}
