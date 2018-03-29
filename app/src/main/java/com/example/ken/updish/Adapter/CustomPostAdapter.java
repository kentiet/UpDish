package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by tanthinh on 3/11/18.
 */

public class CustomPostAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Post> postList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CustomPostAdapter(Activity con)
    {
        this.context = con;
        postList = DatabaseHelper.getInstance().getPostList();
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int i) {
        return postList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.post_list_item, null);
        }

        // If image List has size greater than 0
        if(this.postList.get(i).getImageList().size() > 0)
        {
            ImageView imageViewPostItem =
                    (ImageView) view.findViewById(R.id.imageViewPostItem);

//        imageViewPostItem.setImageResource(R.drawable.food1);

            DisplayMetrics dm = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(dm);

            imageViewPostItem.setMinimumHeight(dm.heightPixels);
            imageViewPostItem.setMinimumWidth(dm.widthPixels);
            imageViewPostItem.setImageBitmap(this.postList.get(i).getImageList().get(0));
        }

        TextView postTitle = (TextView)view.findViewById(R.id.textViewPostTitle);
        postTitle.setText(this.postList.get(i).getTitle());

        TextView postD = (TextView)view.findViewById(R.id.textViewPostDesc);
        postD.setText(this.postList.get(i).getDescription());

        TextView postDa = (TextView)view.findViewById(R.id.textViewPostDate);
        postDa.setText(sdf.format(this.postList.get(i).getDatePost()));

        //Like - Dislike count
        TextView likeCount = (TextView)view.findViewById(R.id.txtView_likeCount_post_item);
        TextView dislikeCount = (TextView)view.findViewById(R.id.txtView_dislikeCount_post_item);
        likeCount.setText(String.valueOf(this.postList.get(i).getVoteUp()));
        dislikeCount.setText(String.valueOf(this.postList.get(i).getVoteDown()));

        //String Color variables
        String colorMainString= "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorMain) & 0x00ffffff);
        String colorDefaultString = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorDefault) & 0x00ffffff);

        String textMultiColor = "<font color="+colorDefaultString+">By</font> <font color="+ colorMainString + ">"+this.postList.get(i).getUser().getUserName()+"</font>";
        TextView postUser = (TextView)view.findViewById(R.id.textViewPostUser);
        postUser.setText(Html.fromHtml(textMultiColor));

        return view;
    }
}
