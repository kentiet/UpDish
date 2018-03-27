package com.example.ken.updish.Database;

import com.example.ken.updish.Interface.DatabaseInterface;

import java.util.ArrayList;

/**
 * Created by tanthinh on 3/19/18.
 */

public class DatabaseHelper implements DatabaseInterface {

    /* Include CRUD operation */
    private static DatabaseHelper databaseHelper = null;
    private static Database database = new Database();

    private DatabaseHelper(){} // Never instantiate

    public static DatabaseHelper getInstance()
    {
        if(databaseHelper == null)
        {
            databaseHelper = new DatabaseHelper();
        }
        return databaseHelper;
    }


}
