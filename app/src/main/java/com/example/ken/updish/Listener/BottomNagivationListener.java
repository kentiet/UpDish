package com.example.ken.updish.Listener;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.ken.updish.Activity.DetailActivity;
import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.R;

import java.util.regex.Pattern;

/**
 * Created by tanthinh on 3/9/18.
 */

public class BottomNagivationListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    Activity context;
    String currentTopActivityName;
    Intent intent;

    /**
     * Bottom Navigation Listener constructor
     * @param con Activity context
     */
    public BottomNagivationListener(Activity con)
    {
        this.context = con;
        ActivityManager mActivityManager =(ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        String mPackageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        String[] splitName = mPackageName.split(Pattern.quote("."));
        currentTopActivityName = splitName[splitName.length - 1];
        Log.e("APP_NAME", currentTopActivityName);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.navigation_main:
//                if(!currentTopActivityName.equals("MainActivity")) {
                    intent = new Intent(context, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    context.startActivity(intent);
//                }
//                context.finish();
                return true;
            case R.id.navigation_new_post:
                Log.e("APP_NAME", "second dashboard");
                intent = new Intent(context, DetailActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(intent);
//                context.finish();
                return true;
            case R.id.navigation_user_control:
                return true;
        }
        return false;
    }

}
