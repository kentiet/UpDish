package com.example.ken.updish.Activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ken.updish.Adapter.CustomPostAdapter;
import com.example.ken.updish.Listener.BottomNagivationListener;
import com.example.ken.updish.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNagivationListener bottomNagivationListener;
    private ListView listPost;
    private CustomPostAdapter customPostAdapter;
    private ArrayList<String> allPostList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add items
        addItems();

        // Initialization
        bottomNagivationListener = new BottomNagivationListener(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationMenuMain);
        navigation.setOnNavigationItemSelectedListener(bottomNagivationListener);

        customPostAdapter = new CustomPostAdapter(this, allPostList);
        listPost = (ListView)findViewById(R.id.listViewMain);
        listPost.setAdapter(customPostAdapter);

//        startActivity(new Intent(MainActivity.this, DetailActivity.class));
//        startActivity(new Intent(MainActivity.this, TestLoginActivity.class));
    }

    private void addItems()
    {
        allPostList.add("I like this dish so much <3");
        allPostList.add("This delicious pizza is the one I am always looking for");
        allPostList.add("Cutest ice cream I have ever seen");
        allPostList.add("I like this dish so much <3");
        allPostList.add("This delicious pizza is the one I am always looking for");
        allPostList.add("Cutest ice cream I have ever seen");
    }
}
