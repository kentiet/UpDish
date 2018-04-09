/*
    This Listener is fired when user clicks on the address displayed in Details Activity
 */

package com.example.ken.updish.Listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Ken on 2018-04-04.
 */

public class DetailMapDialogListener implements AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks{

    Activity context;
    View mapDialogView;
    Dialog mapDialog;
    LatLng latLng;
    MapView mMapView;
    GoogleMap gMap;
    Marker mCurrentLocationMarker;
    LocationRequest mLocationRequest;
    LocationManager mLocationManager;

    public DetailMapDialogListener(Activity _context) {

        this.context = _context;
    }

    private void createDialog() {
        mapDialog = new Dialog(context);

        LayoutInflater featureInflater = context.getLayoutInflater();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mapDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        mapDialogView = featureInflater.inflate(R.layout.detail_map_dialog, null);
        mapDialog.setContentView(mapDialogView);
        mapDialog.show();
        mapDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    public void initMap() {
        mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
       mMapView = (MapView) mapDialogView.findViewById(R.id.mapView);
       MapsInitializer.initialize(context);
       mMapView.onCreate(mapDialog.onSaveInstanceState());
       mMapView.onResume();
       mMapView.getMapAsync(new OnMapReadyCallback() {
           @Override
           public void onMapReady(GoogleMap googleMap) {
               gMap = googleMap;

               MarkerOptions markerOptions = new MarkerOptions();
               markerOptions.position(latLng);
                Log.e("Mapdy", "Called");
               markerOptions.title("Current position");
               markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
               mCurrentLocationMarker = gMap.addMarker(markerOptions);
               gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.2f));
           }


       });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("Map Listener", "Called");
        latLng = DatabaseHelper.getInstance().getLatlng();
        //Log.e("Latlng", String.valueOf(latLng.latitude + " " + latLng.longitude));
        createDialog();
        initMap();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        Log.e("on connect", "onconnected called");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
