package com.example.ken.updish.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.example.ken.updish.R;

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
 * Created by tanthinh on 3/5/18.
 */

public class LoginBackgroundWorker extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    Context context;
    String username, password, title;

    public LoginBackgroundWorker(Context con)
    {
        context = con;
    }

    @Override
    protected String doInBackground(String... params) {
        title = params[0];
        username = params[1];
        password = params[2];

        try
        {
            /* Create URL instance for Http connection with Post method */
            URL url = new URL(SharedResources.getInstance().getStringValue(context,"loginUrl"));
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
    protected void onPreExecute()
    {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
        alertDialog.setMessage(result);
        alertDialog.show();
    }
}
