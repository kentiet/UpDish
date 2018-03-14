package com.example.ken.updish.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ken.updish.Listener.BottomNagivationListener;
import com.example.ken.updish.R;
import com.example.ken.updish.Adapter.ViewPagerAdapter;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private TextView mTextMessage;
    ViewPager viewPager;
    ArrayList<String> mapAddress = new ArrayList<String>();
    Integer mapPointer = new Integer(R.drawable.mappointer);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    mTextMessage.setText(R.string.title_main);
                    return true;
                case R.id.navigation_new_post:
                    mTextMessage.setText(R.string.title_post);
                    return true;
                case R.id.navigation_user_control:
                    mTextMessage.setText(R.string.title_setting);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /* Add */
        mapAddress.add("Hello");

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Image slides
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        //LISTVIEW
        ListView myListViewMap = (ListView)findViewById(R.id.listView_map);

        MapAdapter mapAdapter = new MapAdapter(this, mapAddress, mapPointer);

        myListViewMap.setAdapter(mapAdapter);
        myListViewMap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }

        });
    }

}
