package com.example.ken.updish.Listener;

import android.app.Activity;
import android.view.View;


/**
 * Created by Ken on 2018-03-28.
 */

public class MapsDoneButtonListener implements View.OnClickListener {

    Activity context;

    public MapsDoneButtonListener(Activity _context){
        this.context = _context;
    }
    @Override
    public void onClick(View v) {
        context.finish();
    }
}
