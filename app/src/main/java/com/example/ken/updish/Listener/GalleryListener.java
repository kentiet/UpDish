package com.example.ken.updish.Listener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ken.updish.Adapter.PictureAdapter;
import com.example.ken.updish.BackgroundWorker.LoginBackgroundWorker;
import com.example.ken.updish.Fragment.*;
import com.example.ken.updish.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mijeong on 3/29/2018.
 */
public class GalleryListener implements View.OnClickListener{

    // Constructor
    Activity context;
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ArrayList<Bitmap> bitmapArray = new ArrayList<>();
    private PictureAdapter pictureAdapter;
    private GridView gridViewPicture;
    private Uri data;

    public GalleryListener(Activity context)
    {
        super();
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        onImageGalleryClicked(v);
    }

    public void onImageGalleryClicked(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");
        context.startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

}
