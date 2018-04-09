/*
    This activity is for the Main activity screen when user has just logged in
    and it will navigate to this activity. This activity contains 3 fragments screen
    and a bottom navigation menu
 */

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
    private HomeFragment homeFragment;
    private PostFragment postFragment;
    private UserFragment userFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("Main activity", "onCreate", null);
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
        homeFragment = new HomeFragment();
        postFragment = new PostFragment();
        userFragment = new UserFragment();

        this.setFragment(homeFragment);
        currentFragment = homeFragment;

        bottomNagivationListener = new BottomNagivationListener(this, homeFragment, postFragment, userFragment);
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

        Log.e("Current Fragment: ", currentFragment.toString());

        if(currentFragment == homeFragment)
        {
            PostListBackgroundWorker postWorker = new PostListBackgroundWorker(this);
            postWorker.execute();
        }

    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    public void setHomeFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public PostFragment getPostFragment() {
        return postFragment;
    }

    public void setPostFragment(PostFragment postFragment) {
        this.postFragment = postFragment;
    }

    public UserFragment getUserFragment() {
        return userFragment;
    }

    public void setUserFragment(UserFragment userFragment) {
        this.userFragment = userFragment;
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }
}
