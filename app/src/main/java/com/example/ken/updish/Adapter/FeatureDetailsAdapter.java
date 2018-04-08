package com.example.ken.updish.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Feature;
import com.example.ken.updish.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by mijeong on 4/5/2018.
 */

public class FeatureDetailsAdapter extends BaseAdapter {

    //Get Feature Attributes
    private ArrayList<String> featureList;

    Activity context;

    public FeatureDetailsAdapter(Activity _context, String type) {
        super();
        this.context = _context;
        if(type.equals("positive"))
        {
            this.featureList = DatabaseHelper.getInstance().getCurrentDetailsPost().getPositiveRate();
        }else
        {
            this.featureList = DatabaseHelper.getInstance().getCurrentDetailsPost().getNegativeRate();
        }

    }

    @Override
    public int getCount() {
        return featureList.size();
    }

    @Override
    public Object getItem(int i) {
        return featureList.get(i);
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
        feature.setText(featureList.get(i));

        return view;
    }
}
