/*
    This class is a wrapper class for SharePreferences - simple use
 */

package com.example.ken.updish.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tanthinh on 3/25/18.
 */

public class SharedResources{

    private static SharedResources sharedResources = null;

    public static SharedResources getInstance()
    {
        if(sharedResources == null)
        {
            sharedResources = new SharedResources();
        }
        return sharedResources;
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("updishPrefs", Context.MODE_PRIVATE);
    }

    public void addStringValue(Context context, String key, String value)
    {
        getPrefs(context).edit().putString(key, value).commit();
    }

    public String getStringValue(Context context, String key)
    {
        return getPrefs(context).getString(key, "N/A");
    }
}
