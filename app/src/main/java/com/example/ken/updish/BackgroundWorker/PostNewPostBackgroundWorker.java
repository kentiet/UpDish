package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by tanthinh on 4/5/18.
 */

public class PostNewPostBackgroundWorker extends AsyncTask<String, Void, String> {

    Activity context;

    public PostNewPostBackgroundWorker(Activity con)
    {
        this.context = con;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}