/*
    This Listener is fired when user clicks on the Map
 */

package com.example.ken.updish.Listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.example.ken.updish.Activity.MapsActivity;

/**
 * Created by Ken on 2018-03-28.
 */

public class StartMapsListener implements View.OnClickListener {

    private Activity context;
    //EditText location;

    public StartMapsListener(Activity _context) {
        this.context = _context;
    }

    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context, MapsActivity.class));
    }
}
