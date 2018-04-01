package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Comment;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by mijeonglee on 2018. 3. 19..
 */

public class CommentAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<Comment> listComment = DatabaseHelper.getInstance().getCurrentDetailsPost().getCommentList();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CommentAdapter(Activity context){
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return listComment.size();
    }

    @Override
    public Object getItem(int i) {
        return listComment.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.comment_layout, null);
        }
        TextView txtViewCommentInfo = (TextView)view.findViewById(R.id.txt_commentInfo);
        TextView txtViewCommentDate = (TextView)view.findViewById(R.id.txt_commentDate);
        TextView txtViewComment = (TextView)view.findViewById(R.id.txt_comment);
/*
        String colorMainString= "#" + Integer.toHexString(ContextCompat.getColor(, R.color.colorMain) & 0x00ffffff);
        String colorDefaultString = "#" + Integer.toHexString(ContextCompat.getColor(DetailActivity.this, R.color.colorDefault) & 0x00ffffff);

        String textMultiColor = "<font color="+colorMainString+">"+ userName.get(i) +"</font> <font color="+ colorMainString + "> Posted on "+ commentDate +"</font>";
        txtViewCommentInfo.setText(Html.fromHtml(textMultiColor));
 */
        txtViewCommentInfo.setText(listComment.get(i).getUser().getUserName());
        txtViewCommentDate.setText(sdf.format(listComment.get(i).getDate_comment()));

        txtViewComment.setText(listComment.get(i).getContent());
        txtViewComment.setHeight(155);
        return view;
    }
}
