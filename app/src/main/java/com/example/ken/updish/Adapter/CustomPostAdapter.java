package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.updish.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by tanthinh on 3/11/18.
 */

public class CustomPostAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> postList;

    public CustomPostAdapter(Activity con, ArrayList<String> anyList)
    {
        this.context = con;
        this.postList = anyList;
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

        ImageView imageViewPostItem =
                (ImageView) view.findViewById(R.id.imageViewPostItem);
        imageViewPostItem.setImageResource(R.drawable.food1);

        TextView postTitle = (TextView)view.findViewById(R.id.textViewPostTitle);
        postTitle.setText(postList.get(i));

        TextView postDesc = (TextView)view.findViewById(R.id.textViewPostDesc);
        postDesc.setText("this is a very long text to test for the description this is a very long text to test for the description this is a very long text to test for the description");

        TextView postDate = (TextView)view.findViewById(R.id.textViewPostDate);
        postDate.setText("Posted on 03/05/2018");

        //String Color variables
        String colorMainString= "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorMain) & 0x00ffffff);
        String colorDefaultString = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorDefault) & 0x00ffffff);

        String textMultiColor = "<font color="+colorDefaultString+">By</font> <font color="+ colorMainString + ">Tan Thinh</font>";
        TextView postUser = (TextView)view.findViewById(R.id.textViewPostUser);
        postUser.setText(Html.fromHtml(textMultiColor));

        return view;
    }
}
