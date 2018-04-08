package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;

import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Fragment.PostFragment;
import com.example.ken.updish.Model.Feature;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.Model.User;
import com.example.ken.updish.Utility.ConnectionHelper;
import com.example.ken.updish.Utility.SharedResources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ken on 2018-04-05.
 */

public class FeatureListBackgroundWork extends AsyncTask<String,Void,String> {

    Activity context;
    private PostFragment pfrag;

    public FeatureListBackgroundWork(Activity _context, PostFragment pfrag) {
        this.context = _context;
        this.pfrag = pfrag;
    }


    @Override
    protected String doInBackground(String... strings) {
        String urlWithId = SharedResources.getInstance().getStringValue(this.context,"featureUrl");
        ConnectionHelper connection = new ConnectionHelper(context,urlWithId,"GET");

        String result = connection.connect("", ConnectionHelper.SEND_REQUEST_NO_PARAMETER);
        return result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ArrayList<Feature> featureList = new ArrayList<>();
        MainActivity mainActivity = (MainActivity)context;

        try
        {
            //Convert to JSONArray
            JSONArray jsonArray = new JSONArray(result);
            //Iterating through JSON array
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                Feature f = new Feature();

                f.setFeature(obj.getString("name"));
                Log.e("feature each", f.getFeature());
                featureList.add(f);
            }
            //Log.e("Feauture bg", featureList.toString());
            DatabaseHelper.getInstance().setNewFeatureList(featureList);
            mainActivity.setFragment(pfrag);

        }catch(JSONException jsonex)
        {
            Log.e("Updish", "JSON Exception" + jsonex.getMessage(), null);
        }catch(Exception ex)
        {
            Log.e("Updish", "General Exception: " + ex.getMessage(), null);
        }
    }
}
