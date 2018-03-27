package com.example.ken.updish.Utility;

import android.app.Activity;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tanthinh on 3/27/18.
 */

public class ConnectionHelper {

    SharedResources sr = SharedResources.getInstance();
    Activity context;
    HttpURLConnection connection;

    public ConnectionHelper(Activity context, URL url, String requestMethod){
        this.context = context;
        init(url, requestMethod);
    }

    private void init(URL url, String requestMethod)
    {
        try
        {
            connection = (HttpURLConnection)url.openConnection();

            if(requestMethod.equalsIgnoreCase("POST") ||
                    requestMethod.equalsIgnoreCase("GET") )
            {
                connection.setRequestMethod(requestMethod);
                connection.setDoOutput(true);
                connection.setDoInput(true);
            }
        }catch(Exception ex)
        {
            Log.e(sr.getStringValue(context, "appName"), "Error in init() ConnectionHelper", null);
        }
    }

    public String doConnect(String dataSent)
    {
        return "A";
    }
}
