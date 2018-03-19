package com.example.ken.updish.Database;

import com.example.ken.updish.Interface.DatabaseInterface;

import java.util.ArrayList;

/**
 * Created by tanthinh on 3/19/18.
 */

public class Database implements DatabaseInterface{

    private static Database database = null;

    private ArrayList<String> userList;


    private Database(){} // Never instantiate

    public static Database getInstance()
    {
        if(database == null)
        {
            database = new Database();
        }
        return database;
    }



}
