package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ken.updish.Model.Feature;
import com.example.ken.updish.R;

import java.util.ArrayList;

/**
 * Created by Ken on 2018-04-05.
 */

public class SpinnerFeatureAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Feature> featureList = new ArrayList<>();
    Spinner spFeature;

    public SpinnerFeatureAdapter(Activity con, ArrayList<Feature> fList) {
        this.context = con;
        this.featureList = fList;
//        this.spFeature = spFeature;
    }

    @Override
    public int getCount() {
        return featureList.size();
    }

    @Override
    public Object getItem(int position) {
        return featureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.spinner_detial_layout, null, true);
        }

        TextView feature = (TextView) convertView.findViewById(R.id.txtSpinnerDetail);
        feature.setText(featureList.get(position).getFeature());

        return convertView;
    }
}
