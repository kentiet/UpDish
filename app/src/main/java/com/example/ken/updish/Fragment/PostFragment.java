package com.example.ken.updish.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ken.updish.Activity.MapsActivity;
import com.example.ken.updish.Adapter.FeatureAdapter;
import com.example.ken.updish.Model.Feature;
import com.example.ken.updish.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    Activity context;
    View view;
    private TextView mTextMessage;

    // Maps Related Variable
    PlaceAutocompleteFragment autocompleteFragment;
    private GoogleMap mMap;
    LocationManager mLocationManager;
    private Marker mCurrentLocationMarker;
    SupportMapFragment mapFragment;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;



    // Default current Latlng
    private double currentLong = -122.084;
    private double currentLat = 37.4219983;

    // Feature related
    private AlertDialog.Builder spinnerDialogBuilder;
    private View spinnerDialogView;
    private AlertDialog b;
    private ArrayList<Feature> myProFeatureList = new ArrayList<>();
    private ArrayList<Feature> myConsFeatureList = new ArrayList<>();
    private Spinner fType;
    private Spinner feature;
    private String sltFeatureType;
    private String sltFeature;
    private ListView lvProFeature;
    private ListView lvConFeature;
    FeatureAdapter proAdapter;
    FeatureAdapter consAdapter;

    public PostFragment() {
        // Required empty public constructor
        Log.e("Updish", "Test fragment constructor", null);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);

        context = (Activity)getActivity();

        Button btn_new = (Button)view.findViewById(R.id.btn_Newpost);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show();
            }
        });

        /* KEN */


        /* Maps Part */
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buildGoogleApiClient();

        //Call location service
        mLocationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        autocompleteFragment = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.autocomplete_address);
        View autocompleteView = (View) view.findViewById(R.id.place_autocomplete_search_button);


        autocompleteView.setVisibility(View.GONE);
        // End maps

        /* Features Part */

        createDSpinnerDialog();

        fType = (Spinner) spinnerDialogView.findViewById(R.id.spnFeatureType);
        feature = (Spinner) spinnerDialogView.findViewById(R.id.spnFeature);



        final Button addPros = (Button)view.findViewById(R.id.btnAddPros);
        final Button addCons = (Button)view.findViewById(R.id.btnAddCons);

        buttonClickedHandler(addCons);
        buttonClickedHandler(addPros);

//        lvProFeature.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                myProFeatureList.remove(i);
//            }
//        });
        return view;
    }



    //------------ KEN -----------------//

    /* Feature Part */
    private void populateProList() {
        addProFeature(sltFeatureType, sltFeature);
        lvProFeature = (ListView)getActivity().findViewById(R.id.lvProsFeature);
        proAdapter = new FeatureAdapter(context, myProFeatureList);
        lvProFeature.setAdapter(proAdapter);
    }

    private void populateConsList() {
        addConsFeature(sltFeatureType, sltFeature);
        lvConFeature = (ListView)getActivity().findViewById(R.id.lvConsFeature);
        consAdapter = new FeatureAdapter(context, myConsFeatureList);
        lvConFeature.setAdapter(consAdapter);
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

    private void buttonClickedHandler(final Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        setFeatureOnType();
                        sltFeatureType = fType.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                feature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        sltFeature = feature.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                switch (b.getId()) {
                    case R.id.btnAddCons:
                        consSpinnerDialogHandler();
                        break;
                    case R.id.btnAddPros:
                        proSpinnerDialogHandler();
                        break;
                }

            }
        });
    }

    private void clearSpinnerDialogView(){
        if(spinnerDialogView.getParent() != null) {
            ((ViewGroup)spinnerDialogView.getParent()).removeView(spinnerDialogView);
        }
    }

    private void addProFeature(String t, String f) {
        Feature mFeature = new Feature(t, f);
        myProFeatureList.add(mFeature);
    }

    private void addConsFeature(String t, String f) {
        Feature mFeature = new Feature(t, f);
        myConsFeatureList.add(mFeature);
    }

    private void createDSpinnerDialog() {
        spinnerDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater featureInflater = getLayoutInflater();
        spinnerDialogView = featureInflater.inflate(R.layout.add_feature_layout, null);
        spinnerDialogBuilder.setView(spinnerDialogView);
        spinnerDialogBuilder.setTitle("Please tell us your feelings!");
        spinnerDialogBuilder.setMessage("");
    }

    private void setFeatureOnType() {
        sltFeatureType = fType.getSelectedItem().toString();
        setFeatureSpinnerItem(sltFeatureType);

    }

    private void setFeatureSpinnerItem (String t){
        String[] entry;
        ArrayAdapter<String> spinnerAdapter;

        switch (t) {
            case "Price":
                Log.e("Price", "Price selected");
                //feature.getResources().getStringArray(R.array.feature_price);
                entry = getResources().getStringArray(R.array.feature_price);
                spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, entry);
                feature.setAdapter(spinnerAdapter);
                break;
            case "Taste":
                entry = getResources().getStringArray(R.array.feature_taste);
                spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, entry);
                feature.setAdapter(spinnerAdapter);
                break;
            case "Location":
                entry = getResources().getStringArray(R.array.feature_location);
                spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, entry);
                feature.setAdapter(spinnerAdapter);
                break;
        }

    }



    /* GOOGLE MAPS PART */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(context,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        99 );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                        context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        99 );
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(context,
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Toast.makeText(context, place.getAddress(), Toast.LENGTH_SHORT).show();
                currentLong = place.getLatLng().longitude;
                currentLat = place.getLatLng().latitude;
            }

            @Override
            public void onError(Status status) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.e("onRdy", "Map Ready");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);

                LatLng latLng = new LatLng(currentLat, currentLong);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrentLocationMarker = mMap.addMarker(markerOptions);

            } else {

                Toast myToast = Toast.makeText(context, "error", Toast.LENGTH_SHORT);
                myToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 20, 0);
                myToast.show();
                checkLocationPermission();
            }
        } else {
            //buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }
    //------------ KEN -----------------//

}
