package com.example.ken.updish.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ken.updish.R;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    public static final int IMAGE_GALLERY_REQUEST = 20;

    Activity context;
    private TextView mTextMessage;
    private ArrayList<Bitmap> bitmapArray = new ArrayList<>();

    public PostFragment() {
        // Required empty public constructor
        Log.e("Updish", "Test fragment constructor", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        context = (Activity)getActivity();

        Button btn_new = (Button)view.findViewById(R.id.btn_Newpost);
        final Button btn_picture = (Button)view.findViewById(R.id.btn_picture);

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show();
            }
        });
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Gallery button clicked", Toast.LENGTH_LONG).show();
                onImageGalleryClicked(btn_picture);
            }
        });

        return view;
    }
    public void onImageGalleryClicked(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            InputStream inputStream;

            try{
                //inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(image, 100, 100, false);

                bitmapArray.add(resizedBitmap);

                bitmapArray
            }
        }
    }
}
