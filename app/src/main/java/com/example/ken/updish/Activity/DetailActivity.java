package com.example.ken.updish.Activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.ken.updish.Listener.BottomNagivationListener;
import com.example.ken.updish.R;
import com.example.ken.updish.Adapter.ViewPagerAdapter;

public class DetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNagivationListener bottomNagivationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        bottomNagivationListener = new BottomNagivationListener(this);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationMenuDetail);
//        navigation.setOnNavigationItemSelectedListener(bottomNagivationListener);

        //Image slides
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

    }

}
