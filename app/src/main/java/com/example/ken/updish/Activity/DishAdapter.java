package com.example.ken.updish.Activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.updish.R;

import java.util.ArrayList;

public class DishAdapter extends BaseAdapter {
    ArrayList<String> title;
    ArrayList<String> description;
    ArrayList<Integer> picture;
    Activity context;

    public DishAdapter(Activity context, ArrayList<String> title, ArrayList<String> description, ArrayList<Integer> picture){
        super();
        this.context = context;
        this.picture = picture;
        this.description = description;
        this.title = title;
    }
    @Override
    public int getCount() {
        return picture.size();
    }

    @Override
    public Object getItem(int i) {
        return picture.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.dish_layout, null);
        }

        TextView dishTitle = (TextView)view.findViewById(R.id.txt_food);
        dishTitle.setText(title.get(i));
        TextView dishDesc = (TextView)view.findViewById(R.id.txt_description);
        dishDesc.setText(description.get(i));
        ImageView imgDish = (ImageView)view.findViewById(R.id.img_food);
        imgDish.setImageResource(picture.get(i));

        return view;
    }
}
