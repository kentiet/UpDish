package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.R;
import com.example.ken.updish.Utility.ConnectionHelper;
import com.example.ken.updish.Utility.SharedResources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tanthinh on 3/5/18.
 */

public class LoginBackgroundWorker extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    Activity context;
    String username, password, title;
    ProgressDialog loadingDialog;

    public LoginBackgroundWorker(Activity con)
    {
        context = con;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(
                context, "Fetching data", "Please wait....",true, false);
    }

    @Override
    protected String doInBackground(String... params)
    {
        title = params[0];
        username = params[1];
        password = params[2];

        Log.e("Updish test", title + username + password);

        try
        {
            SystemClock.sleep(1200); // Pretend it's connecting to the server

            String post_data =  URLEncoder.encode("username", "UTF-8" ) + "=" + URLEncoder.encode(username, "UTF-8" ) + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            // Connection
            String urlWithId = SharedResources.getInstance().getStringValue(context,"loginUrl");
            Log.e("Updish url ID",  urlWithId, null);
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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        loadingDialog.dismiss();
        alertDialog = new AlertDialog.Builder(context).create();

        // If login successfully
        if(result == null)
        {
            alertDialog.setTitle("Cannot connect to Updish server");
            alertDialog.setMessage("Please check your connection and try again.");
            alertDialog.show();
        }else
        {
            // Create Simple Dialog to show user's login information
            try
            {
                JSONObject obj = new JSONObject(result);
                String successMsg = obj.getString("success");
                String usernameLogin = obj.getString("username");
                alertDialog.setTitle("Login Status");

                if(successMsg.equalsIgnoreCase("true"))
                {
                    alertDialog.setCancelable(false); // Lock user's action
                    alertDialog.setMessage("Login successfully");
                    alertDialog.show();

                    //Set user
                    DatabaseHelper.getInstance().setCurrentUser(usernameLogin, "noEmailyet@changeinLoginbackgroundworker.com", new String[10]);

                    /* Create a task after X seconds to dismiss the dialog
                       and jump to Main Activity
                    */
                    TimerTask tsk = new TimerTask(){

                        @Override
                        public void run() {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            alertDialog.dismiss();
                            context.finish();
                        }
                    };
                    Timer opening = new Timer();
                    opening.schedule(tsk, 1000);

                }else
                {
                    alertDialog.setMessage("Login failed. Please try again later.");
                    alertDialog.show();
                }
            }catch(JSONException jsonex)
            {
                Log.e("JSONex postex loginbgwk", jsonex.getMessage());
            }

        }

    }
}
