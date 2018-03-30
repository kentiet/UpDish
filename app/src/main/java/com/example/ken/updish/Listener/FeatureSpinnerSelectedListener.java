package com.example.ken.updish.Listener;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by Ken on 2018-03-29.
 */

public class FeatureSpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

    Activity context;
    String sFeature;
    Spinner feature;

    public FeatureSpinnerSelectedListener(Activity _context) {
        this.context = _context;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sFeature = feature.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
