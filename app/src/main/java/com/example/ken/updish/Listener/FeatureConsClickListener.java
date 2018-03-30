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

public class FeatureConsClickListener implements View.OnClickListener {
    Activity context;
    FeatureSpinnerSelectedListener mFSL;
    Spinner spCon;
    ListView lvConsFeature;
    FeatureAdapter consAdapter;
    Button btn;
    private ArrayList<Feature> myConsFeatureList = new ArrayList<>();
    private AlertDialog.Builder spinnerDialogBuilder;
    private View spinnerDialogView;
    private AlertDialog b;

    public FeatureConsClickListener(Activity _context, Button _btn) {

        this.context = _context;
        //this.lvConsFeature = _lvConsFeature;
        this.btn = _btn;
        //this.feature = _feature;
        mFSL = new FeatureSpinnerSelectedListener(context);


    }

    @Override
    public void onClick(View v) {
        lvConsFeature = (ListView)context.findViewById(R.id.lvConsFeature);

        createSpinnerDialog();
        spCon = spinnerDialogView.findViewById(R.id.spnFeature);

        setFeatureSpinnerItem(btn);
        spCon.setOnItemSelectedListener(mFSL);
        setConsAdapter();

    }
    private void setConsAdapter() {
        consAdapter = new FeatureAdapter(context, myConsFeatureList);
        lvConsFeature.setAdapter(consAdapter);
    }
    private void createSpinnerDialog() {
        spinnerDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater featureInflater = context.getLayoutInflater();
        spinnerDialogView = featureInflater.inflate(R.layout.add_feature_layout, null);
        spinnerDialogBuilder.setView(spinnerDialogView);
        spinnerDialogBuilder.setTitle("Please tell us your feelings!");
        spinnerDialogBuilder.setMessage("");
    }

    private void addConsFeature(String f) {
        Feature mFeature = new Feature(f);
        myConsFeatureList.add(mFeature);
    }


    private void populateConsList() {
        addConsFeature(mFSL.sFeature);
    }

    private void clearSpinnerDialogView(){
        if(spinnerDialogView.getParent() != null) {
            ((ViewGroup)spinnerDialogView.getParent()).removeView(spinnerDialogView);
        }
    }

    private void consSpinnerDialogHandler() {
        spinnerDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                populateConsList();
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
            case R.id.btnAddCons:
                entry = context.getResources().getStringArray(R.array.feature_cons);
                spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, entry);
                spCon.setAdapter(spinnerAdapter);
                consSpinnerDialogHandler();
                break;
        }
    }}
