package com.example.ken.updish.Listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ken.updish.Adapter.FeatureAdapter;
import com.example.ken.updish.Model.Feature;
import com.example.ken.updish.R;

import java.util.ArrayList;

/**
 * Created by Ken on 2018-03-29.
 */

public class FeatureProClickListener implements View.OnClickListener {

    Activity context;
    FeatureSpinnerSelectedListener mFSL;
    ListView lvProFeature;
    FeatureAdapter proAdapter;
    Spinner feature;
    Button btn;

    private ArrayList<Feature> myProFeatureList = new ArrayList<>();
    private AlertDialog.Builder spinnerDialogBuilder;
    private View spinnerDialogView;
    private AlertDialog b;
    private Spinner spFeature;

    public FeatureProClickListener(Activity _context, Button _btn) {

        this.context = _context;
        //this.lvProFeature = _lvProFeature;
        this.btn = _btn;
        //this.feature = _feature;

        mFSL = new FeatureSpinnerSelectedListener(context);
        //spFeature = mFSL.feature;

    }

    @Override
    public void onClick(View v) {
        createSpinnerDialog();
        feature = spinnerDialogView.findViewById(R.id.spnFeature);


        lvProFeature = (ListView)context.findViewById(R.id.lvProsFeature);
        setFeatureSpinnerItem(btn);

        feature.setOnItemSelectedListener(mFSL);
        setProAdapter();

    }
    private  void setProAdapter() {
        proAdapter = new FeatureAdapter(context, myProFeatureList);
        lvProFeature.setAdapter(proAdapter);
    }
    private void createSpinnerDialog() {
        spinnerDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater featureInflater = context.getLayoutInflater();
        spinnerDialogView = featureInflater.inflate(R.layout.add_feature_layout, null);
        spinnerDialogBuilder.setView(spinnerDialogView);
        spinnerDialogBuilder.setTitle("Please tell us your feelings!");
        spinnerDialogBuilder.setMessage("");
    }
    private void addProFeature(String f) {
        Feature mFeature = new Feature(f);
        myProFeatureList.add(mFeature);
    }


    private void populateProList() {
        addProFeature(mFSL.sFeature);
    }



    private void clearSpinnerDialogView(){
        if(spinnerDialogView.getParent() != null) {
            ((ViewGroup)spinnerDialogView.getParent()).removeView(spinnerDialogView);
        }
    }

    private void proSpinnerDialogHandler() {

        spinnerDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                populateProList();
            }
        });
        spinnerDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        clearSpinnerDialogView();
        b = spinnerDialogBuilder.create();
        b.show();
    }


    private void setFeatureSpinnerItem (Button button){
        String[] entry;
        ArrayAdapter<String> spinnerAdapter;
        switch (button.getId()) {
            case R.id.btnAddPros:
                entry = context.getResources().getStringArray(R.array.feature_pros);
                spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, entry);
                feature.setAdapter(spinnerAdapter);
                proSpinnerDialogHandler();
                break;
        }
    }
}
