package com.example.ken.updish.BackgroundWorker;

import android.os.AsyncTask;

/**
 * Created by tanthinh on 3/27/18.
 */

public class PostListBackgroundWorker extends AsyncTask<String,Void,String> {

    @Override
    protected void onPreExecute()
    {
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
    protected void onPostExecute(String result) {

    }
}
