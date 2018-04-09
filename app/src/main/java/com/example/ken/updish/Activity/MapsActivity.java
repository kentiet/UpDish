/*
    This activity is for the Google Map screen, which use google api
 */
package com.example.ken.updish.Activity;

import android.Manifest;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ken.updish.Listener.MapsDoneButtonListener;

import com.example.ken.updish.R;
import com.example.ken.updish.Utility.SharedResources;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.*;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener,
        GoogleMap.OnMyLocationButtonClickListener{

    PlaceAutocompleteFragment autocompleteFragment;
    private GoogleMap mMap;
    LocationManager mLocationManager;
    private Marker mCurrentLocationMarker;
    SupportMapFragment mapFragment;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    Button mapsDone;
    private boolean cameraMovable;
    // Default current Latlng
    private double currentLong = -123.0062645;
    private double currentLat = 49.22641;

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.e("MAP", "Oncreate", null);

        //Call location service
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Add the autocompletefragmant UI into fragment
        autocompleteFragment= (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_search_input);
//        View autocompleteView = autocompleteFragment.getView();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buildGoogleApiClient();
        mapsDone = (Button) findViewById(R.id.btnLocationSearch);
        MapsDoneButtonListener mDone = new MapsDoneButtonListener(this, mGoogleApiClient);
        mapsDone.setOnClickListener(mDone);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Log.e("onRdy", "Map Ready");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.e("onRdy", "Map Ready 1st");
                mMap.setMyLocationEnabled(true);
                cameraMovable = true;

                LatLng latLng = new LatLng(currentLat, currentLong);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrentLocationMarker = mMap.addMarker(markerOptions);

            } else {

                Toast myToast = Toast.makeText(this, "error", Toast.LENGTH_SHORT);
                myToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 20, 0);
                myToast.show();
                checkLocationPermission();
            }
        } else {
            //buildGoogleApiClient();
            Log.e("onRdy", "Map Ready else");
            mMap.setMyLocationEnabled(true);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        if(mCurrentLocationMarker != null){
            mCurrentLocationMarker.remove();
        }

        Log.e("location changed", "Map changed");


        LatLng latLng = new LatLng(currentLat, currentLong);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrentLocationMarker = mMap.addMarker(markerOptions);

        if(cameraMovable)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.2f));
        cameraMovable = false;

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Toast.makeText(MapsActivity.this, place.getAddress(), Toast.LENGTH_LONG).show();

                // Split String address
                // Format : 700 Royal Ave, New Westminster, BC V3M 5Z5, Canada
                currentLong = place.getLatLng().longitude;
                currentLat = place.getLatLng().latitude;
                SharedResources sr = SharedResources.getInstance();
                sr.addStringValue(MapsActivity.this, "GoogleMapName", place.getName().toString());
                sr.addStringValue(MapsActivity.this, "GoogleMapLocation", place.getAddress().toString());
                sr.addStringValue(MapsActivity.this,"selectedLong", String.valueOf(currentLong));
                sr.addStringValue(MapsActivity.this, "selectedLat", String.valueOf(currentLat));
                cameraMovable = true;

            }

            @Override
            public void onError(Status status) {
            }


        });
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        Log.e("on connect", "onconnected called");
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                            mLocationRequest,
                             this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        99 );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        99 );
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Log.e("Button My location", "Clicked", null);
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.e("ON START", "Connected");
    }


}
