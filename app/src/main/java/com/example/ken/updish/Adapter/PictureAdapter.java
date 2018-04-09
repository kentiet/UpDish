/*
    This adapter is used to display small picture for choosing pictures in Details Activity
 */
package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by mijeonglee on 2018. 3. 26..
 */

public class PictureAdapter extends BaseAdapter {
    private ArrayList<Bitmap> img = new ArrayList<>();
    private Activity context;

    public PictureAdapter(Activity anyContext, ArrayList<Bitmap> anyImg){
        super();
        this.img = anyImg;
        this.context = anyContext;
    }
    @Override
    public int getCount() {
        return img.size();
    }
    @Override
    public Object getItem(int i) {
        return img.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView myImg = new ImageView(context);
        //show the image to the user
        myImg.setImageBitmap(img.get(i));
        myImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
        myImg.setLayoutParams(new GridView.LayoutParams(200, 200));
        return myImg;
    }
}
