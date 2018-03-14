package com.example.ken.updish.Activity;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ken.updish.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> foodTitle = new ArrayList<String>();
    private ArrayList<String> foodDescription = new ArrayList<String>();
    private ArrayList<Integer> foodPic = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add item
        foodTitle.add("My favorite dish in the world");
        foodTitle.add("The first date");
        foodTitle.add("What I ate last night");
        foodTitle.add("If you are looking for sweet dessert");
        foodDescription.add("Description 1");
        foodDescription.add("Description 2");
        foodDescription.add("Description 3");
        foodDescription.add("Description 4");
        foodPic.add(R.drawable.food1_edited);
        foodPic.add(R.drawable.food2_edited);
        foodPic.add(R.drawable.food3_edited);
        foodPic.add(R.drawable.food4_edited);

        startActivity(new Intent(MainActivity.this, DetailActivity.class));

        //ListView
        ListView foodList = (ListView)findViewById(R.id.listView_dishList);
        DishAdapter dishAdapter = new DishAdapter(this, foodTitle, foodDescription, foodPic);
        foodList.setAdapter(dishAdapter);

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

//        startActivity(new Intent(MainActivity.this, TestLoginActivity.class));
    }
}
