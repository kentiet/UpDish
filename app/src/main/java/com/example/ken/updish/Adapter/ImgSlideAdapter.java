package com.example.ken.updish.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.R;

import java.util.ArrayList;

public class ImgSlideAdapter extends PagerAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Bitmap> imageList;
    private ArrayList<String> pageNumber = new ArrayList<>();

    public ImgSlideAdapter(Context context){
        this.context = context;
        imageList= DatabaseHelper.getInstance().getCurrentDetailsPost().getImageList();

        for(int i = 1; i <= imageList.size(); i++)
        {
            pageNumber.add(i + " / " + imageList.size());
        }
    }
    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.imgslide_layout, null);

        //Image
        ImageView img_slide = (ImageView)view.findViewById(R.id.img_slides);
        img_slide.setImageBitmap(imageList.get(position));

        //Slide number
        TextView txtView_SlideNum = (TextView)view.findViewById(R.id.txtView_slides);
        txtView_SlideNum.setText(pageNumber.get(position));

        ViewPager vp = (ViewPager)container;
        vp.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager)container;
        View view = (View)object;
        vp.removeView(view);
    }
}
