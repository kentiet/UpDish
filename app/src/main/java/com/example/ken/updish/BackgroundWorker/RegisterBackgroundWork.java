/*
    This Asynctask Class is used to send request to register for new user
 */

package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.Utility.ConnectionHelper;
import com.example.ken.updish.Utility.SharedResources;

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
 * Created by Ken on 2018-04-04.
 */

public class RegisterBackgroundWork extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    Activity context;
    String username, password, email, title;
    ProgressDialog loadingDialog;

    public RegisterBackgroundWork(Activity _context) {
        this.context = _context;
    }

    @Override
    protected void onPreExecute()
    {
        loadingDialog = ProgressDialog.show(context, "Creating Your Account", "Please wait....",true, true);
    }

    @Override
    protected String doInBackground(String... params) {
        title = params[0];
        username = params[1];
        email = params[2];
        password = params[3];

        try
        {
            SystemClock.sleep(1500); // Pretend it's connecting to the server

            String post_data =  URLEncoder.encode("username", "UTF-8" ) + "=" + URLEncoder.encode(username, "UTF-8" )
                    + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
                    + "&" +
                    URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

            // Connection
            String urlWithId = SharedResources.getInstance().getStringValue(context,"regUrl");
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
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

        if(result == null)
        {
            alertDialog.setTitle("Cannot connect to Updish server");
            alertDialog.setMessage("Please try again later.");
            alertDialog.show();
        }else
        {
            // Create Simple Dialog to show user's login information
            alertDialog.setTitle("Registering Status");
            alertDialog.setMessage(result);
            alertDialog.show();

            if(result.equals("Register successfully."))
            {
                alertDialog.setCancelable(false); // Lock user's action

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
                opening.schedule(tsk, 2000);

            }
        }

    }
}
