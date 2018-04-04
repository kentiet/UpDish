package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.example.ken.updish.Utility.SharedResources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

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
            SystemClock.sleep(2000); // Pretend it's connecting to the server

            /* Create URL instance for Http connection with Post method */
            URL url = new URL(SharedResources.getInstance().getStringValue(context,"regUrl"));
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            /* Create outputStream from httpURLConnection*/
            OutputStream outputStream = httpURLConnection.getOutputStream();

            /* Create buffered writer to write to the outputStream */
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data =  URLEncoder.encode("username", "UTF-8" ) + "=" + URLEncoder.encode(username, "UTF-8" ) + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            /* Create inputStream to receive data return back from the PHP script */
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while((line = bufferedReader.readLine()) != null)
            {
                result += line;
            }

            bufferedReader.close();
            inputStream.close();


            httpURLConnection.disconnect(); // Disconnect HTTP URL connection

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
