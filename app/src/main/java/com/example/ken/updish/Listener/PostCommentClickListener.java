/*
    This Listener is fired when user clicks on the Post button when he wants to post new comments
 */

package com.example.ken.updish.Listener;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.example.ken.updish.BackgroundWorker.PostCommentBackgroundWorker;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tanthinh on 3/31/18.
 */

public class PostCommentClickListener implements View.OnClickListener {

    Activity context;
    Post currentPostDetails = DatabaseHelper.getInstance().getCurrentDetailsPost();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PostCommentClickListener(Activity con)
    {
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        EditText txtAddComment = (EditText)context.findViewById(R.id.txt_comment);

        Calendar ca = Calendar.getInstance();
        String currentDate = sdf.format(ca.getTime());

        PostCommentBackgroundWorker pcbg = new PostCommentBackgroundWorker(context);
        pcbg.execute(txtAddComment.getText().toString(), currentDate,
                DatabaseHelper.getInstance().getCurrentUser().getUserName(),
                String.valueOf(currentPostDetails.getId()));

        txtAddComment.setText("");
    }
}
