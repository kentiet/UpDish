package com.example.ken.updish.Activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.updish.R;

import java.util.ArrayList;


public class MapAdapter extends BaseAdapter {
    Activity context;
    private String name;
    private String address;
    private Integer mapPointer;

    public MapAdapter(Activity context, String name, String address, Integer mapPointer){

        super();
        this.context = context;
        this.name = name;
        this.address = address;
        this.mapPointer = mapPointer;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return name;
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
        resname.setText(name);
        TextView resAddress = (TextView)view.findViewById(R.id.txtView_address);
        resAddress.setText(address);

        return view;
    }
}
