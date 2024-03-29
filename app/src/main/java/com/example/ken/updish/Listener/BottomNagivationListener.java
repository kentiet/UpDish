/*
    This Listener is fired when user clicks on the menu item on BottomNagvigationView
 */

package com.example.ken.updish.Listener;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.ken.updish.Activity.DetailActivity;
import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.BackgroundWorker.FeatureListBackgroundWork;
import com.example.ken.updish.BackgroundWorker.PostListBackgroundWorker;
import com.example.ken.updish.Fragment.HomeFragment;
import com.example.ken.updish.Fragment.PostFragment;
import com.example.ken.updish.Fragment.UserFragment;
import com.example.ken.updish.R;

import java.util.regex.Pattern;

/**
 * Created by tanthinh on 3/9/18.
 */

public class BottomNagivationListener extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private MainActivity context;
    private String currentTopActivityName;
    private Intent intent;
    private HomeFragment homeFragment;
    private PostFragment postFragment;
    private UserFragment userFragment;

    /**
     * Bottom Navigation Listener constructor
     * @param con Activity context
     */
    public BottomNagivationListener(MainActivity con, HomeFragment h, PostFragment p, UserFragment u)
    {
        this.context = con;
        homeFragment = h;
        postFragment = p;
        userFragment = u;

//        context.setFragment(homeFragment);

//        ActivityManager mActivityManager =(ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
//        String mPackageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getClassName();
//        String[] splitName = mPackageName.split(Pattern.quote("."));
//        currentTopActivityName = splitName[splitName.length - 1];
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        MainActivity mainActivity = (MainActivity)context;
        switch (item.getItemId()) {

            case R.id.navigation_main:
//                if(!currentTopActivityName.equals("MainActivity")) {
//                    intent = new Intent(context, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    context.startActivity(intent);
//                }
//                context.finish();
                mainActivity.setCurrentFragment(homeFragment);
                context.setFragment(homeFragment);


                // Fetch data from server to Home fragment
                PostListBackgroundWorker postWorker = new PostListBackgroundWorker(context);
                postWorker.execute();

                return true;
            case R.id.navigation_new_post:
//                Log.e("APP_NAME", "second dashboard");

                mainActivity.setCurrentFragment(postFragment);
                context.setFragment(postFragment);

                FeatureListBackgroundWork fLGb = new FeatureListBackgroundWork(context, postFragment);
                fLGb.execute();

                return true;
            case R.id.navigation_user_control:
                mainActivity.setCurrentFragment(userFragment);
                context.setFragment(userFragment);
                return true;
        }
        return false;
    }

}
