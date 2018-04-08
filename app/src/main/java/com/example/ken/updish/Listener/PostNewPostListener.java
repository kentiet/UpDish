package com.example.ken.updish.Listener;

import android.app.Activity;
import android.view.View;

import com.example.ken.updish.BackgroundWorker.PostNewPostBackgroundWorker;

/**
 * Created by tanthinh on 4/5/18.
 */

public class PostNewPostListener implements View.OnClickListener {

    Activity context;

    public PostNewPostListener(Activity con)
    {
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        PostNewPostBackgroundWorker postnewBgwk = new PostNewPostBackgroundWorker(context);
        postnewBgwk.execute();
    }
}
