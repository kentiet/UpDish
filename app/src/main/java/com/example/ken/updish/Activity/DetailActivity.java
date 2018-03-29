package com.example.ken.updish.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ken.updish.Adapter.CommentAdapter;
import com.example.ken.updish.Adapter.MapAdapter;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.R;
import com.example.ken.updish.Adapter.imgSlideAdapter;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class DetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private String mapAddress;
    private String mapRestaurant;
    private Integer mapPointer;
    private ArrayList<String> commentUserName = new ArrayList<>();
    private ArrayList<String> commentDate = new ArrayList<>();
    private ArrayList<String> commentDesc = new ArrayList<>();
    private Post currentPostDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");

        DatabaseHelper.getInstance().setCurrentDetailsPost(DatabaseHelper.getInstance().getPostById(id));
        currentPostDetails = DatabaseHelper.getInstance().getCurrentDetailsPost();
        mapPointer = new Integer(R.drawable.mappointer);

        initPostTitle();
        initMapItems();
        addItemComment();

        //Back button
        ImageButton imgBtnBack = (ImageButton)findViewById(R.id.btn_back);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Back to Main Activity - destroy
                finish();
            }
        });

        //Map Text
        ListView myListViewMap = (ListView)findViewById(R.id.listView_map);
        MapAdapter mapAdapter = new MapAdapter(this,
                mapRestaurant, mapAddress, mapPointer);
        myListViewMap.setAdapter(mapAdapter);
        myListViewMap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Google map Activity
            }
        });

        //Image slides
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        imgSlideAdapter slideAdapter = new imgSlideAdapter(this);
        viewPager.setAdapter(slideAdapter);

        //Comments
        ListView myListViewComments = (ListView)findViewById(R.id.listView_comments);
        CommentAdapter commentAdapter = new CommentAdapter(this, commentUserName, commentDesc, commentDate);
        myListViewComments.setAdapter(commentAdapter);
        setListViewHeightBasedOnItems(myListViewComments);

        //Add Comments
        Button btnComment = (Button)findViewById(R.id.btn_postComment);
        EditText txtAddComment = (EditText)findViewById(R.id.txt_comment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When comment button clicked
            }
        });
    }
    protected void initPostTitle(){
        try {
            //Get Title Data from MainActivity

            TextView pTitle = (TextView)findViewById(R.id.txt_postTitle);
            pTitle.setText(currentPostDetails.getTitle());

            TextView pDateUser = (TextView)findViewById(R.id.txt_postDate);
            String colorMainString= "#" + Integer.toHexString(ContextCompat.getColor(DetailActivity.this, R.color.colorMain) & 0x00ffffff);
            String colorDefaultString = "#" + Integer.toHexString(ContextCompat.getColor(DetailActivity.this, R.color.colorDefault) & 0x00ffffff);

            String textMultiColor = "<font color="+colorDefaultString+">"+ currentPostDetails.getDatePost().toString() +" By</font> <font color="+ colorMainString + ">"+currentPostDetails.getUser().getUserName()+"</font>";
            pDateUser.setText(Html.fromHtml(textMultiColor));

            TextView pDesc = (TextView)findViewById(R.id.txt_description);
            pDesc.setText(currentPostDetails.getDescription());

        }catch(Exception ex){
            //Toast
        }
    }
    protected void initMapItems(){
        mapRestaurant = "Douglas College";
        mapAddress = "Royal Avenue, New Westminster, BC V3M 5Z5";
    }

    protected void addItemComment(){

        //Comment Information
        commentUserName.add("helloworld123");
        commentUserName.add("sdfew4332");

        commentDesc.add("Not good at all");
        commentDesc.add("Too sweet");

        commentDate.add("03/08/18");
        commentDate.add("04/05/18");
    }

    //https://stackoverflow.com/questions/1778485/android-listview-display-all-available-items-without-scroll-with-static-header
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
}
