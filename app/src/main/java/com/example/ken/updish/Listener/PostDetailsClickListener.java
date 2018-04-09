/*
    This Listener is fired when user clicks on the single item in the Listview of Home Fragment
 */

package com.example.ken.updish.Listener;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.ken.updish.Activity.DetailActivity;
import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.BackgroundWorker.PostDetailsBackgroundWorker;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Post;

/**
 * Created by tanthinh on 3/29/18.
 */

public class PostDetailsClickListener implements AdapterView.OnItemClickListener {

    MainActivity context;

    public PostDetailsClickListener(Activity con)
    {
        this.context = (MainActivity)con;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //start activity with each title data clicked
        Post tempPost = DatabaseHelper.getInstance().getPostAtPosition(position);
        PostDetailsBackgroundWorker npbw = new PostDetailsBackgroundWorker(context);
        npbw.execute(String.valueOf(tempPost.getId()));
    }
}
