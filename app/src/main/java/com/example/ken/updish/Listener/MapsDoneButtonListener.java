package com.example.ken.updish.Listener;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.ken.updish.Activity.MapsActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * Created by Ken on 2018-03-28.
 */

public class MapsDoneButtonListener implements View.OnClickListener {

    Activity context;
    GoogleApiClient mGoogleApiClient;

    public MapsDoneButtonListener(Activity _context, GoogleApiClient gac){
        this.context = _context;
        this.mGoogleApiClient = gac;

    }
    @Override
    public void onClick(View v) {
        MapsActivity mapsActivity = (MapsActivity)context;
        if(ContextCompat.checkSelfPermission(mapsActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mapsActivity);
        }

        context.finish();
    }
}
