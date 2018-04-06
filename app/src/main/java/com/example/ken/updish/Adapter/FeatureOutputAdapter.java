package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ken.updish.Model.Feature;
import com.example.ken.updish.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 300260935 on 4/5/2018.
 */

public class FeatureOutputAdapter extends BaseAdapter {
    //Get Feature Attributes
    ArrayList<String> featureArr = new ArrayList<>();
    Activity context;

    public FeatureOutputAdapter(Activity _context, ArrayList<String> featureList) {
        super();
        this.context = _context;
        this.featureArr = featureList;
    }

    @Override
    public int getCount() {
        return featureArr.size();
    }

    @Override
    public Object getItem(int i) {
        return featureArr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.feature_output_layout, null);
        }

        TextView feature = (TextView)view.findViewById(R.id.txtView_feature);
        feature.setText(featureArr.get(i));
        return view;
    }
}
