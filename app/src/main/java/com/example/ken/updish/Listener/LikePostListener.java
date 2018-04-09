/*
    This Listener is fired when user clicks LIKE or DISLIKE Post
 */

package com.example.ken.updish.Listener;

import android.app.Activity;
import android.view.View;

import com.example.ken.updish.BackgroundWorker.LikePostBackgroundWorker;
import com.example.ken.updish.Database.DatabaseHelper;

/**
 * Created by tanthinh on 4/1/18.
 */

public class LikePostListener implements View.OnClickListener {

    Activity context;
    String type;

    public LikePostListener(Activity con, String type)
    {
        this.context = con;
        this.type = type;
    }

    @Override
    public void onClick(View v) {
        LikePostBackgroundWorker lpbgw = new LikePostBackgroundWorker(context, type);

        lpbgw.execute(String.valueOf(DatabaseHelper.getInstance().getCurrentDetailsPost().getId()),
                DatabaseHelper.getInstance().getCurrentUser().getUserName(),
                type);
    }
}
