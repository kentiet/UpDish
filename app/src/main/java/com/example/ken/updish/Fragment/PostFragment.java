package com.example.ken.updish.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ken.updish.Activity.MapsActivity;
import com.example.ken.updish.ActivityResultBus;
import com.example.ken.updish.Adapter.FeatureAdapter;
import com.example.ken.updish.Listener.StartMapsListener;
import com.example.ken.updish.Model.Feature;

import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ken.updish.Adapter.PictureAdapter;

import com.example.ken.updish.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

public class PostFragment extends Fragment {


    //    View view;
    View view;
    SharedPreferences sharedPreferences;
    String postLocation;
    EditText sLocation;

    private Activity context;
    private TextView mTextMessage;

    private ArrayList<Bitmap> bitmapArray = new ArrayList<>();
    private PictureAdapter pictureAdapter;
    private Uri data;
    private Intent photoPickerIntent;
    private GridView gridViewPicture;
    public static final int IMAGE_GALLERY_REQUEST = 20;
    int clicked = 0;


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
    //private String sltFeatureType;
    private String sltFeature;
    private ListView lvProFeature;
    private ListView lvConFeature;
    FeatureAdapter proAdapter;
    FeatureAdapter consAdapter;
    Button addPros;
    Button addCons;


    public PostFragment() {
        // Required empty public constructor
//        Log.e("Updish", "Test fragment constructor", null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);


        context = (Activity) getActivity();

        final Button btn_new = (Button) view.findViewById(R.id.btn_Newpost);
        final Button btn_picture = (Button) view.findViewById(R.id.btn_picture);
        //final TextView txtLocation = (TextView)view.findViewById(R.id.txt_map);
        gridViewPicture = (GridView) view.findViewById(R.id.gridView_picture);


        //Post Button
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
            }
        });

        //Gallery
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked < 5) {
                    clicked++;
                    onImageGalleryClicked(btn_picture);
                } else {
                    Toast.makeText(context, "Post can contain maximum 5 photos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /* KEN */
        /* Maps Part */


        StartMapsListener sMap = new StartMapsListener(context);
        sLocation = (EditText) view.findViewById(R.id.post_location);
        sLocation.setOnClickListener(sMap);


        // End maps

        /* Features Part */

        createDSpinnerDialog();

        //fType = (Spinner) spinnerDialogView.findViewById(R.id.spnFeatureType);
        feature = (Spinner) spinnerDialogView.findViewById(R.id.spnFeature);

        final Button addPros;
        final Button addCons;

        addPros = (Button) view.findViewById(R.id.btnAddPros);
        addCons = (Button) view.findViewById(R.id.btnAddCons);


        buttonClickedHandler(addCons);
        buttonClickedHandler(addPros);

        return view;
    }
    //------------ KEN -----------------//

    /* Feature Part */
    private void populateProList() {
        addProFeature(sltFeature);
        lvProFeature = (ListView) getActivity().findViewById(R.id.lvProsFeature);
        proAdapter = new FeatureAdapter(context, myProFeatureList);
        lvProFeature.setAdapter(proAdapter);
    }

    private void populateConsList() {
        addConsFeature(sltFeature);
        lvConFeature = (ListView) getActivity().findViewById(R.id.lvConsFeature);
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

    private void buttonClickedHandler(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                feature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        sltFeature = feature.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                setFeatureSpinnerItem(btn);
                switch (btn.getId()) {
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

    private void clearSpinnerDialogView() {
        if (spinnerDialogView.getParent() != null) {
            ((ViewGroup) spinnerDialogView.getParent()).removeView(spinnerDialogView);
        }
    }

    private void addProFeature(String f) {
        Feature mFeature = new Feature(f);
        myProFeatureList.add(mFeature);
    }

    private void addConsFeature(String f) {
        Feature mFeature = new Feature(f);
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

//    private void setFeatureOnCall() {
//        sltFeatureType = fType.getSelectedItem().toString();
//        setFeatureSpinnerItem(sltFeatureType);
//
//    }

    private void setFeatureSpinnerItem(Button button) {
        String[] entry;
        ArrayAdapter<String> spinnerAdapter;
        switch (button.getId()) {
            case R.id.btnAddPros:
                entry = getResources().getStringArray(R.array.feature_pros);
                spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, entry);
                feature.setAdapter(spinnerAdapter);
                break;
            case R.id.btnAddCons:
                entry = getResources().getStringArray(R.array.feature_cons);
                spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, entry);
                feature.setAdapter(spinnerAdapter);
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        postLocation = null;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        postLocation = sharedPreferences.getString("GoogleSearchName", null);
        sLocation.setText(postLocation);
        Log.e("sharePref", "Location set text");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            InputStream inputStream;
            try {
                inputStream = context.getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(image, 100, 100, false);
                bitmapArray.add(resizedBitmap);
                pictureAdapter = new PictureAdapter(context, bitmapArray);
                gridViewPicture = (GridView) context.findViewById(R.id.gridView_picture);
                gridViewPicture.setAdapter(pictureAdapter);
                gridViewPicture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Picture selected for the main
                        bitmapArray.remove(bitmapArray.get(i));
                        gridViewPicture.setAdapter(pictureAdapter);
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    //------------ KEN -----------------//

    public void onImageGalleryClicked(View view) {
        photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }
}

