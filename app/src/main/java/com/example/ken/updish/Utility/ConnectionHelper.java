package com.example.ken.updish.Utility;

import android.app.Activity;
import android.util.Log;

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
 * Created by tanthinh on 3/27/18.
 */

public class ConnectionHelper {

    public static int SEND_REQUEST_NO_PARAMETER = 1;
    public static int SEND_REQUEST_WITH_PARAMETERS = 2;

    SharedResources sr = SharedResources.getInstance();
    Activity context;
    HttpURLConnection connection;
    String urlString;
    String requestMethod;

    public ConnectionHelper(Activity context, String urlString, String requestMethod){
        this.context = context;
        this.urlString = urlString;
        this.requestMethod = requestMethod;
    }

    public String connect(String post_data, int typeRequest)
    {
        try
        {
            /* Create URL instance for Http connection with Post method */
            URL url = new URL(this.urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod(this.requestMethod);

            httpURLConnection.setDoInput(true);
            if(typeRequest == SEND_REQUEST_WITH_PARAMETERS)
            {
                httpURLConnection.setDoOutput(true);

                /* Create outputStream from httpURLConnection*/
                OutputStream outputStream = httpURLConnection.getOutputStream();

                /* Create buffered writer to write to the outputStream */
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
            }

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
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
