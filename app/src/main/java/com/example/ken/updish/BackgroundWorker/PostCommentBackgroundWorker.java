package com.example.ken.updish.BackgroundWorker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ListView;

import com.example.ken.updish.Adapter.CommentAdapter;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Comment;
import com.example.ken.updish.Model.User;
import com.example.ken.updish.R;
import com.example.ken.updish.Utility.ConnectionHelper;
import com.example.ken.updish.Utility.SharedResources;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by tanthinh on 3/31/18.
 */

public class PostCommentBackgroundWorker extends AsyncTask<String, Void, String> {

    Activity context;
    ProgressDialog loadingDialog;
    String postContent, dateComment, username, postId;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PostCommentBackgroundWorker(Activity con)
    {
        this.context = con;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        loadingDialog = ProgressDialog.show(
                context, "Posting comment.", "Please wait....",true, false);

    }

    @Override
    protected String doInBackground(String... params) {

        postContent = params[0];
        dateComment = params[1];
        username = params[2];
        postId = params[3];

        try
        {
//            SystemClock.sleep(1500); // Pretend it's connecting to the server

            String post_data =
                    URLEncoder.encode("content", "UTF-8" ) + "=" + URLEncoder.encode(postContent, "UTF-8" )
                    + "&" +
                    URLEncoder.encode("date_comment", "UTF-8") + "=" + URLEncoder.encode(dateComment, "UTF-8")
                    + "&" +
                    URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                    + "&" +
                    URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(postId, "UTF-8");

            // Connection
            String urlWithId = SharedResources.getInstance().getStringValue(context,"commentUrl");
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

        loadingDialog.dismiss(); // Turn off loading
        Log.e("after post: ", result, null);

        // Add comment
        try
        {
            Comment newComment = new Comment();
            newComment.setId(Integer.parseInt(result));
            newComment.setContent(this.postContent);
            newComment.setUser(new User(this.username, "", new String[10]));
            newComment.setDate_comment(sdf.parse(dateComment));
            ArrayList<Comment> currentPostCommentList = DatabaseHelper.getInstance().getCurrentDetailsPost().getCommentList();
            currentPostCommentList.add(0, newComment);

            //Notify
            ListView myListViewComments = (ListView)context.findViewById(R.id.listView_comments);
            CommentAdapter cmadapter = (CommentAdapter)myListViewComments.getAdapter();
            cmadapter.notifyDataSetChanged();

            //Reload Details activity - NO NEED TO RELOAD ACTIVITY
//            DetailActivity detailActivity = (DetailActivity)context;
//            detailActivity.recreate();
//            Intent intent = context.getIntent();
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            detailActivity.startActivity(intent);
//            detailActivity.finish();

        }catch(ParseException pe)
        {
            pe.printStackTrace();
        }


    }
}
