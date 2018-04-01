package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.example.ken.updish.Utility.ConnectionHelper;
import com.example.ken.updish.Utility.SharedResources;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tanthinh on 4/1/18.
 */

public class LikePostBackgroundWorker extends AsyncTask<String, Void, String> {

    Activity context;
    ProgressDialog loadingDialog;
    String postId, username, type;

    public LikePostBackgroundWorker(Activity con)
    {
        this.context = con;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(
                context, "Voting.", "Please wait....",true, false);

    }

    @Override
    protected String doInBackground(String... params) {

        postId = params[0];
        username = params[1];
        type = params[2];

        try
        {
//            SystemClock.sleep(1500); // Pretend it's connecting to the server

            String post_data =
                    URLEncoder.encode("type", "UTF-8" ) + "=" + URLEncoder.encode(type, "UTF-8" )
                            + "&" +
                            URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                            + "&" +
                            URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(postId, "UTF-8");

            // Connection
            String urlWithId = SharedResources.getInstance().getStringValue(context,"commentUrl");
            ConnectionHelper connection = new ConnectionHelper(context,urlWithId,"POST");

            String result = connection.connect(post_data, ConnectionHelper.SEND_REQUEST_WITH_PARAMETERS);
            return result;

        }catch(UnsupportedEncodingException uee)
        {
            uee.printStackTrace();
        }


        return null;

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        loadingDialog.dismiss(); // Turn off
    }
}
