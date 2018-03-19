package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ken.updish.Activity.DetailActivity;
import com.example.ken.updish.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by mijeonglee on 2018. 3. 19..
 */

public class CommentAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<String> userName = new ArrayList<>();
    private ArrayList<String> commentDesc = new ArrayList<>();
    private ArrayList<String> commentDate = new ArrayList<>();

    public CommentAdapter(Activity context, ArrayList<String> anyUsername, ArrayList<String> anyCommentDesc, ArrayList<String> anyDate){
        super();
        this.context = context;
        this.userName = anyUsername;
        this.commentDesc = anyCommentDesc;
        this.commentDate = anyDate;
    }

    @Override
    public int getCount() {
        return userName.size();
    }

    @Override
    public Object getItem(int i) {
        return userName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.layout_comments, null);
        }
        TextView txtViewCommentInfo = (TextView)view.findViewById(R.id.txt_commentInfo);
        TextView txtViewComment = (TextView)view.findViewById(R.id.txt_comment);
/*
        String colorMainString= "#" + Integer.toHexString(ContextCompat.getColor(, R.color.colorMain) & 0x00ffffff);
        String colorDefaultString = "#" + Integer.toHexString(ContextCompat.getColor(DetailActivity.this, R.color.colorDefault) & 0x00ffffff);

        String textMultiColor = "<font color="+colorMainString+">"+ userName.get(i) +"</font> <font color="+ colorMainString + "> Posted on "+ commentDate +"</font>";
        txtViewCommentInfo.setText(Html.fromHtml(textMultiColor));
 */
        txtViewCommentInfo.setText(userName.get(i) + " posted on " + commentDate.get(i));
        txtViewComment.setText(commentDesc.get(i));

        return view;
    }
}
