package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.example.ken.updish.Activity.DetailActivity;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.Utility.ConnectionHelper;
import com.example.ken.updish.Utility.SharedResources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tanthinh on 4/1/18.
 */

public class LikePostBackgroundWorker extends AsyncTask<String, Void, String> {

    Activity context;
    ProgressDialog loadingDialog;
    String postId, username, type;

    public LikePostBackgroundWorker(Activity con, String type)
    {
        this.context = con;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(
                context, "Voting.", "Please wait....",true, false);

    }

    @Override
    protected String doInBackground(String... params) {

        postId = params[0];
        username = params[1];
        type = params[2];

        try
        {
//            SystemClock.sleep(1500); // Pretend it's connecting to the server

            String post_data =
                    URLEncoder.encode("type", "UTF-8" ) + "=" + URLEncoder.encode(type, "UTF-8" )
                            + "&" +
                            URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                            + "&" +
                            URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(postId, "UTF-8");

            // Connection
            String urlWithId = SharedResources.getInstance().getStringValue(context,"likeUrl");
            ConnectionHelper connection = new ConnectionHelper(context,urlWithId,"POST");

            String result = connection.connect(post_data, ConnectionHelper.SEND_REQUEST_WITH_PARAMETERS);
            return result;

        }catch(UnsupportedEncodingException uee)
        {
            uee.printStackTrace();
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
        loadingDialog.dismiss(); // Turn off
        DetailActivity detailActivity = (DetailActivity)context;

        try
        {
            JSONObject obj = new JSONObject(result);
//            Log.e("postObj toString", result);

            String method = obj.getString("method");
            String success = obj.getString("success");
            String typeChoice = obj.getString("type");

            if(success.equalsIgnoreCase("true"))
            {
                JSONObject postObj = new JSONObject(obj.getString("post"));
                int voteUp = Integer.parseInt(postObj.getString("voteup"));
                int voteDown = Integer.parseInt(postObj.getString("votedown"));
                Post currentPostDt = DatabaseHelper.getInstance().getCurrentDetailsPost();

                // If insert into relationship
                if(method.equalsIgnoreCase("insert"))
                {
                    if(typeChoice.equalsIgnoreCase("like"))
                    {
                        incrementThumbUpAction(currentPostDt, voteUp, detailActivity);

                    }else if(typeChoice.equalsIgnoreCase("dislike"))
                    {
                        incrementThumbDownAction(currentPostDt, voteDown, detailActivity);
                    }

                // Modify or delete
                }else if(method.equalsIgnoreCase("modify"))
                {
                    if(typeChoice.equalsIgnoreCase("like"))
                    {
                        incrementThumbUpAction(currentPostDt, voteUp, detailActivity);
                        decrementThumbDownAction(currentPostDt, voteDown, detailActivity);

                    }else if(typeChoice.equalsIgnoreCase("dislike"))
                    {
                        incrementThumbDownAction(currentPostDt, voteDown, detailActivity);
                        decrementThumbUpAction(currentPostDt, voteUp, detailActivity);
                    }

                } else if(method.equalsIgnoreCase("delete"))
                {
                    decrementThumbUpAction(currentPostDt, voteUp, detailActivity);
                    decrementThumbDownAction(currentPostDt, voteDown, detailActivity);
                }
            }
        }catch(JSONException jsonex)
        {
            Log.e("Ex: post likepostbgwk", jsonex.getMessage(),null);
        }

    }

    private void incrementThumbUpAction(Post currentPostDt, int numVoteUp, DetailActivity da)
    {
        currentPostDt.setLikeStatus("like");
        currentPostDt.setVoteUp(numVoteUp);
        da.setThumbUpImageColor();
        da.getTxtLike().setText(String.valueOf(currentPostDt.getVoteUp()));
    }

    private void incrementThumbDownAction(Post currentPostDt, int numVoteDown, DetailActivity da)
    {
        currentPostDt.setLikeStatus("dislike");
        currentPostDt.setVoteDown(numVoteDown);
        da.setThumbDownImageColor();
        da.getTxtDislike().setText(String.valueOf(currentPostDt.getVoteDown()));
    }

    private void decrementThumbUpAction(Post currentPostDt, int numVoteUp, DetailActivity da)
    {
        currentPostDt.setLikeStatus("like");
        currentPostDt.setVoteUp(numVoteUp);
        da.setThumbUpImageNormal();
        da.getTxtLike().setText(String.valueOf(currentPostDt.getVoteUp()));
    }

    private void decrementThumbDownAction(Post currentPostDt, int numVoteDown, DetailActivity da)
    {
        currentPostDt.setLikeStatus("dislike");
        currentPostDt.setVoteDown(numVoteDown);
        da.setThumbDownImageNormal();
        da.getTxtDislike().setText(String.valueOf(currentPostDt.getVoteDown()));
    }
}
