package com.example.ken.updish.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.updish.R;

public class imgSlideAdapter extends PagerAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3};
    private String [] pageNumber = {"1/3", "2/3", "3/3"};

    public imgSlideAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return images.length;
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
        img_slide.setImageResource(images[position]);

        //Slide number
        TextView txtView_SlideNum = (TextView)view.findViewById(R.id.txtView_slides);
        txtView_SlideNum.setText(pageNumber[position]);

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