package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.R;

import java.util.ArrayList;

/*
    This adapter is used to display Map for details activity
 */
public class MapAdapter extends BaseAdapter {

    Activity context;
    private Post currentPostDetail;
    private Integer mapPointer = new Integer(R.drawable.mappointer);

    public MapAdapter(Activity context){

        super();
        this.context = context;
        currentPostDetail = DatabaseHelper.getInstance().getCurrentDetailsPost();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return currentPostDetail.getLocation().getName();
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();
        if(view == null){
            view = inflater.inflate(R.layout.map_layout, null);
        }
        ImageView imgMapPointer = (ImageView)view.findViewById(R.id.img_mapPointer);
        imgMapPointer.setImageResource(mapPointer);
        TextView resname = (TextView)view.findViewById(R.id.txtView_resName);
        resname.setText(currentPostDetail.getLocation().getName());
        TextView resAddress = (TextView)view.findViewById(R.id.txtView_address);
        resAddress.setText(DatabaseHelper.getInstance().getCurrentPostFullAddress());

        return view;
    }
}
