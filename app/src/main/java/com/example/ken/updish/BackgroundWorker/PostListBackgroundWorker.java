package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Fragment.HomeFragment;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.Model.User;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tanthinh on 3/27/18.
 */

public class PostListBackgroundWorker extends AsyncTask<String,Void,String> {

    Activity context;
    Dialog mdialog;

    public PostListBackgroundWorker(Activity con)
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
            SystemClock.sleep(1000);

            // Connection
            URL url = new URL(SharedResources.getInstance().getStringValue(this.context,"postUrl"));
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

        }catch (MalformedURLException e) {
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

    @Override
    protected void onPostExecute(String result)
    {
        mdialog.dismiss(); // Dismiss loading
        MainActivity main = (MainActivity)context;
        Fragment currentFragment = (HomeFragment)main.getSupportFragmentManager().getFragments().get(0);
        ArrayList<Post> postList = new ArrayList<>();

        if (currentFragment instanceof HomeFragment) {
            Log.e("Updish", "Inside Post Execute", null);

            try
            {
                //Convert to JSONArray
                JSONArray jsonArray = new JSONArray(result);


                //Iterating through JSON array
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    // Define new post
                    Post tempPost = new Post();
                    JSONObject obj = jsonArray.getJSONObject(i);
                    tempPost.setId(Integer.parseInt(obj.getString("id")));
                    tempPost.setTitle(obj.getString("title"));
                    tempPost.setDescription(obj.getString("description"));
                    tempPost.setVoteUp(Integer.parseInt(obj.getString("voteup")));
                    tempPost.setVoteDown(Integer.parseInt(obj.getString("votedown")));

                    //User
                    User tempUser = new User(obj.getString("username"), "", new String[10]);
                    tempPost.setUser(tempUser);

                    //Image - Bytes - Bitmap
                    byte[] bytes = Base64.decode(obj.getString("image_path"), Base64.DEFAULT);
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    tempPost.getImageList().add(bm);

                    //Date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date tempDate = sdf.parse(obj.getString("date_posted"));
                    tempPost.setDatePost(tempDate);

                    //Add to post List
                    postList.add(tempPost);
                }
                DatabaseHelper.getInstance().setNewPostList(postList);

                //Refresh Fragment
                FragmentTransaction fragTransaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                fragTransaction.detach(currentFragment);
                fragTransaction.attach(currentFragment);
                fragTransaction.commit();

            }catch(JSONException jsonex)
            {
                Log.e("Updish", "JSON Exception" + jsonex.getMessage(), null);
            }catch(Exception ex)
            {
                Log.e("Updish", "General Exception: " + ex.getMessage(), null);
            }

        }

    }

}
