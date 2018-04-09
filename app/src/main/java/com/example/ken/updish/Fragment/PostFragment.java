/*
    This Post Fragment class is for Second menu item in Main Activity, it will allow users to upload
    Post
 */

package com.example.ken.updish.Fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ken.updish.Activity.MapsActivity;
import com.example.ken.updish.Adapter.FeatureAdapter;

import com.example.ken.updish.BackgroundWorker.LikePostBackgroundWorker;
import com.example.ken.updish.BackgroundWorker.PostNewPostBackgroundWorker;

import com.example.ken.updish.Adapter.SpinnerFeatureAdapter;
import com.example.ken.updish.BackgroundWorker.FeatureListBackgroundWork;

import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Listener.StartMapsListener;
import com.example.ken.updish.Model.Feature;

import android.widget.GridView;
import android.widget.ImageView;

import com.example.ken.updish.Adapter.PictureAdapter;

import com.example.ken.updish.R;
import com.example.ken.updish.Utility.SharedResources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

public class PostFragment extends Fragment {

    //    View view;
    View view;
    String postLocation;
    EditText sLocation;
    EditText namePost;
    EditText txtDescription;
    SharedResources sRc;

    private Activity context;
    private TextView mTextMessage;

    private ArrayList<Bitmap> bitmapArrayGrid = new ArrayList<>();
    private ArrayList<Bitmap> bitmapArrayNormal = new ArrayList<>();
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
    ArrayList<Feature> passFeatureList;
    private ArrayList<String> myProFeatureList = new ArrayList<>();
    private ArrayList<String> myConsFeatureList = new ArrayList<>();
    private Spinner fType;
    private Spinner feature;
    private String sltFeatureType;
    private String sltFeature;
    //private Feature sltFeature;
    private ListView lvProFeature;
    private ListView lvConFeature;
    FeatureAdapter proAdapter;
    FeatureAdapter consAdapter;
    Button addPros;
    Button addCons;

    //Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public PostFragment() {
        // Required empty public constructor
//        Log.e("Updish", "Test fragment constructor", null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);

        context = (Activity)getActivity();


        /********* == START == ********
         *                            *
         * --------- MIJEONG -------- *
         *                            *
         ******************************/

        final Button btn_newPost = (Button)view.findViewById(R.id.btn_Newpost);

        //New Picture Buttonq


        final Button btn_picture = (Button)view.findViewById(R.id.btn_picture);

        //final TextView txtLocation = (TextView)view.findViewById(R.id.txt_map);
        gridViewPicture = (GridView) view.findViewById(R.id.gridView_picture);
        namePost = (EditText)view.findViewById(R.id.txt_nameOfDishes);
        txtDescription = (EditText)view.findViewById(R.id.txt_description);


        //Post Button
        btn_newPost.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                SharedResources sr = SharedResources.getInstance();
                String locationSelected = sr.getStringValue(context, "GoogleMapLocation");
                String locationName = sr.getStringValue(context, "GoogleMapName");
                String locationLong = sr.getStringValue(context, "selectedLong");
                String locationLat = sr.getStringValue(context, "selectedLat");

                Log.e("locationSelected", locationSelected, null);

                // Check name
                if(namePost.getText().length() == 0)
                {
                    Toast.makeText(context, "Please enter a dish's name", Toast.LENGTH_LONG).show();

                // Check array size
                } else if(bitmapArrayNormal.size() <= 0)
                {
                    Toast.makeText(context, "Please add at least 1 food picture", Toast.LENGTH_LONG).show();

                }else if(txtDescription.getText().length() == 0)
                {
                    Toast.makeText(context, "Please describe your dish", Toast.LENGTH_LONG).show();
                }else if(locationSelected.equals("N/A") || locationName.equals("N/A"))
                {
                    Toast.makeText(context, "Please Select a location", Toast.LENGTH_LONG).show();
                }else
                {
                    // Start getting info
                    JSONObject objSend = new JSONObject();

                    try
                    {
                        // Name - Description
                        String sendingPostName = namePost.getText().toString(); // Name
                        String sendingDescription = txtDescription.getText().toString(); // Description

                        // Pictures
                        JSONArray picArr = new JSONArray();
                        for(int i = 0; i < bitmapArrayNormal.size(); i++)
                        {
                            Bitmap curr = bitmapArrayNormal.get(i);
                            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
                            curr.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS);
                            byte[] b = byteArrayOS.toByteArray();
                            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
                            picArr.put(imageEncoded);
                        }

                        // Listview
                        JSONArray prosList = new JSONArray();
                        JSONArray consList = new JSONArray();

                        // List view
                        if(myProFeatureList.size() > 0)
                        {
                            for(int i = 0; i < myProFeatureList.size(); i++)
                            {
                                String tempFeature = myProFeatureList.get(i);
                                prosList.put(tempFeature);
                            }
                        }

                        if(myConsFeatureList.size() > 0)
                        {
                            for(int i = 0; i < myConsFeatureList.size(); i++)
                            {
                                String tempFeature = myConsFeatureList.get(i);
                                consList.put(tempFeature);
                            }
                        }

//                        for(int i = 0; i < lvConFeature.getAdapter().getCount(); i++)
//                        {
//                            Feature tempFeature = (Feature)lvConFeature.getAdapter().getItem(i);
//                            consList.add(tempFeature.getFeature());
//                        }

                        //Location

                        //Put to json obj
                        Calendar cal = Calendar.getInstance();
                        objSend.put("username", DatabaseHelper.getInstance().getCurrentUser().getUserName());
                        objSend.put("title", sendingPostName);
                        objSend.put("description", sendingDescription);
                        objSend.put("date_posted", sdf.format(cal.getTime()));
                        objSend.put("photo", picArr);
                        objSend.put("locationAddress", locationSelected);
                        objSend.put("locationName", locationName);
                        objSend.put("prolist", prosList);
                        objSend.put("conlist", consList);
                        objSend.put("longtitude", locationLong);
                        objSend.put("latitude", locationLat);

//                        Log.e("JSON send", objSend.toString());

                        PostNewPostBackgroundWorker lpbgwk = new PostNewPostBackgroundWorker(context);
                        lpbgwk.execute(objSend);

                    }catch(JSONException jsonex)
                    {
                        Log.e("JSONEx in postfragment", jsonex.getMessage(), null);
                    }

                }

            }
        });


        //Gallery
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmapArrayNormal.size() < 5 && bitmapArrayGrid.size() < 5) {
//                    clicked++;
                    onImageGalleryClicked(btn_picture);
                } else {
                    Toast.makeText(context, "Post can contain maximum 5 photos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /********** == END == ********
         *                            *
         * --------- MIJEONG -------- *
         *                            *
         ******************************/

        //------------------------------------------------------------------------------------------


        /********* == START == ********
         *                            *
         * ----------- KEN ---------- *
         *                            *
         ******************************/

        /* KEN */

        /* Maps Part */


        StartMapsListener sMap = new StartMapsListener(context);
        sLocation = (EditText) view.findViewById(R.id.post_location);

        sLocation.setOnClickListener(sMap);

        // End maps

        /* Features Part */

        createDSpinnerDialog();

        feature = (Spinner) spinnerDialogView.findViewById(R.id.spnFeature);


        addPros = (Button) view.findViewById(R.id.btnAddPros);
        addCons = (Button) view.findViewById(R.id.btnAddCons);

        buttonClickedHandler(addCons);
        buttonClickedHandler(addPros);

        /********** == END == ********
         *                            *
         * ----------- KEN ---------- *
         *                            *
         ******************************/

        return view;
    }


    //++START++ ------------ MIJEONG -----------------//

    public void onImageGalleryClicked(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        //Get file from DIRECTORY PICTURES in android phone
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //Get path, create URI link and set data
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");

        //Start activity result
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            InputStream inputStream;
            try {

                // Create input stream and Get the bitmap
                inputStream = context.getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(image, 100, 100, false);

                // Add to both array size
                bitmapArrayNormal.add(image);
                bitmapArrayGrid.add(resizedBitmap);

                // Create GridView Set adapter for the gridview
                pictureAdapter = new PictureAdapter(context, bitmapArrayGrid);
                gridViewPicture = (GridView) context.findViewById(R.id.gridView_picture);
                gridViewPicture.setAdapter(pictureAdapter);

                // Set listener
                gridViewPicture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Picture selected for the main to remove
                        bitmapArrayGrid.remove(bitmapArrayGrid.get(i));
                        bitmapArrayNormal.remove(bitmapArrayNormal.get(i));
                        gridViewPicture.setAdapter(pictureAdapter);
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        buttonClickedHandler(addCons);
        buttonClickedHandler(addPros);

    }

    //++END++ ------------ MIJEONG -----------------//






    

    //++START++ ------------ KEN -----------------//

    /* Feature Part */
    private void populateProList() {
        addProFeature(sltFeature);
        lvProFeature = (ListView) context.findViewById(R.id.lvProsFeature);
        justifyListViewHeightBasedOnChildren(lvProFeature);
        proAdapter = new FeatureAdapter(context, myProFeatureList);
        lvProFeature.setAdapter(proAdapter);
    }
    private void populateConsList() {
        addConsFeature(sltFeature);
        lvConFeature = (ListView)context.findViewById(R.id.lvConsFeature);
        justifyListViewHeightBasedOnChildren(lvConFeature);
        consAdapter = new FeatureAdapter(context, myConsFeatureList);
        lvConFeature.setAdapter(consAdapter);
    }

    //Listview not scrollable
    public static void justifyListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight()+50;
        }
        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
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

    private void isExistFeature(ArrayList<Feature> fList, Feature f) {
        if(!fList.isEmpty()) {
            for (int i = 0; i < fList.size(); i++) {
                if(f.equals(fList.get(i))) {
                    fList.remove(i);
                }
            }
        }
    }

    private void buttonClickedHandler(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passFeatureList = DatabaseHelper.getInstance().getFeatureList();
                SpinnerFeatureAdapter sAdapter = new SpinnerFeatureAdapter(context, passFeatureList);
                feature.setAdapter(sAdapter);

                feature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Feature slF = (Feature) feature.getSelectedItem();
                        sltFeature = slF.getFeature();
                        //Check if selected feature exists
                        isExistFeature(passFeatureList, slF);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                //setFeatureSpinnerItem(btn);
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
        myProFeatureList.add(f);
    }

    private void addConsFeature(String f) {
        myConsFeatureList.add(f);
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
//

    private void setFeatureSpinnerItem(Button button) {
        String[] entry;
        ArrayAdapter<String> spinnerAdapter;
        switch (button.getId()) {
            case R.id.btnAddPros:
                entry = getResources().getStringArray(R.array.feature_array);
                spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, entry);
                feature.setAdapter(spinnerAdapter);
                break;
            case R.id.btnAddCons:
                entry = getResources().getStringArray(R.array.feature_array);
                spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, entry);
                feature.setAdapter(spinnerAdapter);
                break;
        }
    }

    public ArrayList<Bitmap> getBitmapArrayGrid() {
        return bitmapArrayGrid;
    }

    public void setBitmapArrayGrid(ArrayList<Bitmap> bitmapArrayGrid) {
        this.bitmapArrayGrid = bitmapArrayGrid;
    }

    public ArrayList<Bitmap> getBitmapArrayNormal() {
        return bitmapArrayNormal;
    }

    public void setBitmapArrayNormal(ArrayList<Bitmap> bitmapArrayNormal) {
        this.bitmapArrayNormal = bitmapArrayNormal;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        postLocation = null;
        sRc = SharedResources.getInstance();
        postLocation = sRc.getStringValue(context, "GoogleMapName");
        Log.e("resume", postLocation);
        if(!postLocation.equals("N/A")) {
            sLocation.setText(postLocation);
        }
    }
    //++END++------------ KEN -----------------//
}

