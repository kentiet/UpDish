package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.Window;

import com.example.ken.updish.Activity.DetailActivity;
import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Comment;
import com.example.ken.updish.Model.Location;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.Model.User;
import com.example.ken.updish.R;
import com.example.ken.updish.Utility.ConnectionHelper;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tanthinh on 3/28/18.
 */

public class PostDetailsBackgroundWorker extends AsyncTask<String, Void, String> {

    Activity context;
    Dialog mdialog;
    String currentId;

    public PostDetailsBackgroundWorker(Activity con)
    {
        this.context = con;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mdialog = new Dialog(context);
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mdialog.setContentView(R.layout.custom_progress_dialog);
        mdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mdialog.setCancelable(false);
        mdialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        String idParam = params[0];
        currentId = idParam;

        // Connection
        String urlWithId = SharedResources.getInstance().getStringValue(this.context,"postUrl") + "/" + idParam;
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
        mdialog.dismiss(); // Dismiss loader

//        Log.e("NewPostBW onPostExecute", result, null);

        mdialog.dismiss(); // Dismiss loading
        MainActivity main = (MainActivity)context;

        try {
            //Convert to JSONArray
            JSONObject obj = new JSONObject(result);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // define Post object
            // id, title, description, date_posted, voteup, votedown, username, image_path
            Post tempPost = new Post();

            // Post
            tempPost.setId(Integer.parseInt(obj.getString("id")));
            tempPost.setTitle(obj.getString("title"));
            tempPost.setDescription(obj.getString("description"));
            tempPost.setVoteUp(Integer.parseInt(obj.getString("voteup")));
            tempPost.setVoteDown(Integer.parseInt(obj.getString("votedown")));

            User tempUser = new User(obj.getString("username"), "", new String[10]);
            tempPost.setUser(tempUser);

            // Date
            Date tempDate = sdf.parse(obj.getString("date_posted"));
            tempPost.setDatePost(tempDate);

            //Image - Bytes - Bitmap
            JSONArray jarray = obj.getJSONArray("image_path");
            if(jarray != null)
            {
                for(int j = 0; j < jarray.length(); j++)
                {
                    byte[] bytes = Base64.decode(jarray.getString(j), Base64.DEFAULT);
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    tempPost.getImageList().add(bm);
                }
            }

            /* === Location === */
            // location_id, name, streetNumber, streetName,
            // postalcode, city, province, longtitude, latitude
            Location loc = new Location();
            loc.setId(Integer.parseInt(obj.getString("votedown")));
            loc.setName(obj.getString("name"));
            loc.setStreetNumber(obj.getString("streetNumber"));
            loc.setStreetName(obj.getString("streetName"));
            loc.setPostalCode(obj.getString("postalcode"));
            loc.setCity(obj.getString("city"));
            loc.setProvince(obj.getString("province"));
            tempPost.setLocation(loc);

            // Comment
            // comment_id, content, date_comment, username, post_id
            JSONArray jcarray = obj.getJSONArray("comment");
            if(jcarray != null)
            {
                for(int j = 0; j < jcarray.length(); j++)
                {
                    JSONObject coObj = jcarray.getJSONObject(j);
                    User comUser = new User(coObj.getString("username"), "", new String[10]);
                    Comment tempCo = new Comment();
                    tempCo.setUser(comUser);
                    tempCo.setId(Integer.parseInt(coObj.getString("comment_id")));
                    tempCo.setContent(coObj.getString("content"));
                    tempCo.setDate_comment(sdf.parse(coObj.getString("date_comment")));
                    tempPost.getCommentList().add(tempCo);
                }
            }

            // Set current
            DatabaseHelper.getInstance().setCurrentDetailsPost(tempPost);

            //Refresh activity
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", Integer.parseInt(currentId));
            context.startActivity(intent);

//            Intent intent = getIntent();
////            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            finish();
//            startActivity(intent);


        }catch(JSONException jsonex)
        {
            Log.e("Updish", "JSON Exception in NewPostBGW" + jsonex.getMessage(), null);
        }catch(Exception ex)
        {
            Log.e("Updish", "General Exception in NewPostBGW " + ex.getMessage(), null);
        }

    }
}
