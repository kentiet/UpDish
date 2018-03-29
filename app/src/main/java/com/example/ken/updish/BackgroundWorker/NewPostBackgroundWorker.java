package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;

import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.R;
import com.example.ken.updish.Utility.SharedResources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by tanthinh on 3/28/18.
 */

public class NewPostBackgroundWorker extends AsyncTask<String, Void, String> {

    Activity context;
    Dialog mdialog;

    public NewPostBackgroundWorker(Activity con)
    {
        this.context = con;
    }

    @Override
    protected void onPreExecute()
    {
        mdialog = new Dialog(context);
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mdialog.setContentView(R.layout.custom_progress_dialog);
        mdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mdialog.setCancelable(false);
        mdialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try
        {
            String idParam = params[0];

            // Connection
            String urlWithId = SharedResources.getInstance().getStringValue(this.context,"postUrl") + "/" + idParam;
            Log.e("NewPostBW doInBG", urlWithId, null);

            URL url = new URL(urlWithId);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);

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
        } catch(Exception ex)
        {

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
        mdialog.dismiss(); // Dismiss loader

        Log.e("NewPostBW onPostExecute", result, null);

        mdialog.dismiss(); // Dismiss loading
        MainActivity main = (MainActivity)context;

        try {
            //Convert to JSONArray
            JSONArray jsonArray = new JSONArray(result);


            //Iterating through JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
            }
        }catch(JSONException jsonex)
        {
            Log.e("Updish", "JSON Exception in NewPostBGW" + jsonex.getMessage(), null);
        }catch(Exception ex)
        {
            Log.e("Updish", "General Exception in NewPostBGW " + ex.getMessage(), null);
        }

    }
}
