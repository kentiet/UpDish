package com.example.ken.updish.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
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

public class BackgroundWorker extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    Context context;

    public BackgroundWorker(Context con)
    {
        context = con;
    }

    @Override
    protected String doInBackground(String... params) {
        String APP_NAME = context.getString(R.string.app_name);
        String type = params[0];
        String login_url = "http://10.0.2.2:8888/androidGroupProject/login.php";
//        String login_url = "http://192.168.0.15:8888/login.php";

        if(type.equals("login"))
        {
            Log.e(APP_NAME,"inside Login");
            try
            {
                String user_name = params[1];
                String password = params[2];

                Log.e(APP_NAME, user_name);
                Log.e(APP_NAME, password);

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                Log.e(APP_NAME, "After setting httpURLConnection");

                OutputStream outputStream = httpURLConnection.getOutputStream();

                Log.e(APP_NAME, "Before bufferWriter");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                Log.e(APP_NAME, "Before post_Data");

                String post_data =  URLEncoder.encode("username", "UTF-8" ) + "=" + URLEncoder.encode(user_name, "UTF-8" ) + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                Log.e(APP_NAME,post_data);
                bufferedWriter.write(post_data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

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
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    @Override
    protected void onPreExecute()
    {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }
}
