package com.example.ken.updish.Activity;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.ken.updish.BackgroundWorker.PostListBackgroundWorker;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Fragment.HomeFragment;
import com.example.ken.updish.Fragment.PostFragment;
import com.example.ken.updish.Fragment.UserFragment;
import com.example.ken.updish.Listener.BottomNagivationListener;
import com.example.ken.updish.R;
import com.example.ken.updish.Utility.BottomNavigationViewHelper;
import com.example.ken.updish.Utility.SharedResources;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNagivationListener bottomNagivationListener;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout mainFrame;
    private HomeFragment homeFragment;
    private PostFragment postFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); // Initialization

        // Fetch data from server to Home fragment initially
        PostListBackgroundWorker postWorker = new PostListBackgroundWorker(this);
        postWorker.execute();

        // Set currentUser
//        DatabaseHelper.getInstance().setCurrentUser("tan", "", new String[10]);
//        startActivity(new Intent(MainActivity.this, MapsActivity.class));
//        startActivity(new Intent(MainActivity.this, UserLoginActivity.class));

    }

    private void init()
    {
        mainFrame = (FrameLayout)findViewById(R.id.frameLayoutMain);

        homeFragment = new HomeFragment();
        postFragment = new PostFragment();
        userFragment = new UserFragment();

        bottomNagivationListener = new BottomNagivationListener(this, mainFrame, homeFragment, postFragment, userFragment);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationMenuMain);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView, 10, 10, 10, 10);
        BottomNavigationViewHelper.resizeItems(this, bottomNavigationView, 30, 30);



        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNagivationListener);
    }

    public void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Main activity", "onRestart", null);
        PostListBackgroundWorker postWorker = new PostListBackgroundWorker(this);
        postWorker.execute();
    }
}
