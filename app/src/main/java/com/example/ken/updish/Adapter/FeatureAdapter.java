package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.ken.updish.R;

/**
 * Created by Ken on 2018-03-22.
 */

public class FeatureAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> featureTypesList = new ArrayList<>();
    ArrayList<String> featuresList = new ArrayList<>();

    public FeatureAdapter(Activity _context, ArrayList<String> _featureTypes, ArrayList<String> _features) {
        this.context = _context;
        this.featureTypesList = _featureTypes;
        this.featuresList = _features;
    }

    @Override
    public int getCount() {
        return featuresList.size();
    }

    @Override
    public Object getItem(int position) {
        return featuresList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.feature_layout, null);
        }

        TextView featureTypes = (TextView) convertView.findViewById(R.id.txtFeatureType);
        TextView features = (TextView) convertView.findViewById(R.id.txtFeatures);

        featureTypes.setText(featureTypesList.get(position) + " :");
        features.setText(featuresList.get(position));

        return convertView;
    }
}
