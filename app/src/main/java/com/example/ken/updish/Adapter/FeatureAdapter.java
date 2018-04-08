package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.ken.updish.Model.Feature;
import com.example.ken.updish.R;

/**
 * Created by Ken on 2018-03-22.
 */

public class FeatureAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> featureList = new ArrayList<>();

    public FeatureAdapter(Activity _context, ArrayList<String> _featureList) {
        super();
        this.context = _context;
        this.featureList = _featureList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.feature_layout, null, true);
        }

        //TextView featureTypes = (TextView) convertView.findViewById(R.id.txtFeatureType);
        TextView features = (TextView) convertView.findViewById(R.id.txtFeatures);

        //featureTypes.setText(featureList.get(position).getType() + " :");
        features.setText(featureList.get(position));

        features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                featureList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
