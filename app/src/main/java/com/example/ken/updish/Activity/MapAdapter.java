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
    private ArrayList<String> address;
    private Integer mapPointer;

    public MapAdapter(Activity context, ArrayList<String> address, Integer mapPointer){
        super();
        this.context = context;
        this.address = address;
        this.mapPointer = mapPointer;
    }

    @Override
    public int getCount() {
        return address.size();
    }

    @Override
    public Object getItem(int i) {
        return address.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();
        if(view == null){
            view = inflater.inflate(R.layout.map_layout, null);
        }
        //TextView txtAddress= (TextView)view.findViewById(R.id.txt_address);
        //txtAddress.setText(address.get(i));
        ImageView imgMapPointer = (ImageView)view.findViewById(R.id.img_mapPointer);
        imgMapPointer.setImageResource(mapPointer);
        return view;
    }
}
