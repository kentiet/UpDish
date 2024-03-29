/*
    This activity is for the details activity screen when user chooses a specific
    post from the Home Screen
 */
package com.example.ken.updish.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ken.updish.Adapter.CommentAdapter;
import com.example.ken.updish.Adapter.FeatureDetailsAdapter;
import com.example.ken.updish.Adapter.MapAdapter;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Fragment.PostFragment;
import com.example.ken.updish.Listener.DetailMapDialogListener;
import com.example.ken.updish.Listener.LikePostListener;
import com.example.ken.updish.Listener.PostCommentClickListener;
import com.example.ken.updish.Model.Post;
import com.example.ken.updish.R;
import com.example.ken.updish.Adapter.ImgSlideAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ListView myListViewComments;
    private EditText editTextComment;
    private ImageButton likePostImgBtn;
    private ImageButton dislikePostImgBtn;
    private TextView txtLike;
    private TextView txtDislike;
    private Drawable thumbup;
    private Drawable thumbupCor;
    private Drawable thumbdown;
    private Drawable thumbdownCor;
    private Integer mapPointer;
    private Post currentPostDetails;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ListView listViewFeatures_pro;
    private ListView listViewFeatures_con;
    FeatureDetailsAdapter featureDetailsAdapter;
    private TextView feature_pro;
    private TextView feature_con;

    private ArrayList<String> featureArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.e("Updish", "onCreate Details Activity", null);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");

        currentPostDetails = DatabaseHelper.getInstance().getCurrentDetailsPost();
        mapPointer = new Integer(R.drawable.mappointer);

        displayBackButton(); //Back button
        initPostTitle();
        initLikePostButton();
        displayMapAndImageSlides();
        displayCommentArea();

        //FEATURES
        //addFeatures();
        displayFeaturesPro();
        displayFeaturesCon();
    }
    private void initPostTitle(){
        try {
            //Get Title Data from MainActivity
            TextView pTitle = (TextView)findViewById(R.id.txt_postTitle);
            pTitle.setText(currentPostDetails.getTitle());

            TextView pDateUser = (TextView)findViewById(R.id.txt_postDate);
            String colorMainString= "#" + Integer.toHexString(ContextCompat.getColor(DetailActivity.this, R.color.colorMain) & 0x00ffffff);
            String colorDefaultString = "#" + Integer.toHexString(ContextCompat.getColor(DetailActivity.this, R.color.colorDefault) & 0x00ffffff);

            String formatedDate = sdf.format(currentPostDetails.getDatePost());
            String textMultiColor = "<font color="+colorDefaultString+">"+ formatedDate +"&nbsp;&nbsp;&nbsp;By</font> <font color="+ colorMainString + ">"+currentPostDetails.getUser().getUserName()+"</font>";
            pDateUser.setText(Html.fromHtml(textMultiColor));

            TextView pDesc = (TextView)findViewById(R.id.txt_description);
            pDesc.setText(currentPostDetails.getDescription());

            txtLike = (TextView)findViewById(R.id.txtView_likeCount);
            txtDislike = (TextView)findViewById(R.id.txtView_dislikeCount);

            txtLike.setText(String.valueOf(currentPostDetails.getVoteUp()));
            txtDislike.setText(String.valueOf(currentPostDetails.getVoteDown()));

        }catch(Exception ex){
            Log.e("Updish", "Crashed in initPostTitle - Detail Activity", null);
        }
    }
    private void initLikePostButton()
    {
        Log.e("Init like post button", currentPostDetails.getLikeStatus()+"",null);

        thumbup = this.getResources().getDrawable(R.drawable.thumb_up);
        thumbupCor = this.getResources().getDrawable(R.drawable.thumb_up_colored);
        thumbdown = this.getResources().getDrawable(R.drawable.thumb_down);
        thumbdownCor = this.getResources().getDrawable(R.drawable.thumb_down_colored);

        likePostImgBtn = (ImageButton)findViewById(R.id.imgBtn_like);
        dislikePostImgBtn = (ImageButton)findViewById(R.id.imgBtn_dislike);

        //Set appropriate Drawable
        if(currentPostDetails.getLikeStatus().equalsIgnoreCase("like"))
        {
            likePostImgBtn.setImageDrawable(thumbupCor);
        }else if(currentPostDetails.getLikeStatus().equalsIgnoreCase("dislike"))
        {
            dislikePostImgBtn.setImageDrawable(thumbdownCor);
        }else
        {
            likePostImgBtn.setImageDrawable(thumbup);
            dislikePostImgBtn.setImageDrawable(thumbdown);
        }

        LikePostListener lpbgLike = new LikePostListener(this, "like");
        LikePostListener lpbgDislike = new LikePostListener(this, "dislike");

        likePostImgBtn.setOnClickListener(lpbgLike);
        dislikePostImgBtn.setOnClickListener(lpbgDislike);
    }

    private void displayBackButton()
    {
        ImageButton imgBtnBack = (ImageButton)findViewById(R.id.btn_back);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Back to Main Activity - destroy
                finish();
            }
        });
    }

    private void displayMapAndImageSlides()
    {
        //Map Text
        DetailMapDialogListener mapDialogListener = new DetailMapDialogListener(this);
        ListView myListViewMap = (ListView)findViewById(R.id.listView_map);
        MapAdapter mapAdapter = new MapAdapter(this);
        myListViewMap.setAdapter(mapAdapter);
        myListViewMap.setOnItemClickListener(mapDialogListener);
//        myListViewMap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //Google map Activity
//                Toast.makeText(DetailActivity.this, "Display Map Dialog", Toast.LENGTH_LONG);
//                Log.e("Updish Details Activity", "Resume", null);
//
//            }
//        });
        //Image slides
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        ImgSlideAdapter slideAdapter = new ImgSlideAdapter(this);
        viewPager.setAdapter(slideAdapter);
    }


    //Features
    private void displayFeaturesPro(){
        listViewFeatures_pro = (ListView)findViewById(R.id.listView_feature_pro);
        featureDetailsAdapter = new FeatureDetailsAdapter(this, "positive");
        listViewFeatures_pro.setAdapter(featureDetailsAdapter);
        feature_pro = new TextView(this);
        feature_pro.setText("Pros: ");
        feature_pro.setGravity(Gravity.CENTER);
        feature_pro.setClickable(false);
        feature_pro.setLongClickable(false);
        listViewFeatures_pro.addHeaderView(feature_pro);

        PostFragment.justifyListViewHeightBasedOnChildren(listViewFeatures_pro);
    }
    private void displayFeaturesCon(){
        listViewFeatures_con = (ListView)findViewById(R.id.listView_feature_con);
        featureDetailsAdapter = new FeatureDetailsAdapter(this, "negative");
        listViewFeatures_con.setAdapter(featureDetailsAdapter);
        feature_con = new TextView(this);
        feature_con.setText("Cons: ");
        feature_con.setGravity(Gravity.CENTER);
        feature_con.setClickable(false);
        feature_con.setLongClickable(false);
        listViewFeatures_con.addHeaderView(feature_con);

        PostFragment.justifyListViewHeightBasedOnChildren(listViewFeatures_con);
    }

    private void displayCommentArea()
    {
        //Comments
        myListViewComments = (ListView)findViewById(R.id.listView_comments);
        myListViewComments.setClickable(false);
        myListViewComments.setLongClickable(false);

        CommentAdapter commentAdapter = new CommentAdapter(this);
        myListViewComments.setAdapter(commentAdapter);
        editTextComment = (EditText)findViewById(R.id.txt_comment);
//        txt_commenteditTextComment.setCompoundDrawables(R.drawable.comment, 0,0,0);


        //Add Comments Button
        PostCommentClickListener postCommentClickListener =
                new PostCommentClickListener(this);
        Button btnComment = (Button)findViewById(R.id.btn_postComment);

        btnComment.setOnClickListener(postCommentClickListener);
    }

    public void setThumbUpImageNormal()
    {
        likePostImgBtn.setImageDrawable(thumbup);
    }

    public void setThumbUpImageColor()
    {
        likePostImgBtn.setImageDrawable(thumbupCor);
    }

    public void setThumbDownImageNormal()
    {
        dislikePostImgBtn.setImageDrawable(thumbdown);
    }

    public void setThumbDownImageColor()
    {
        dislikePostImgBtn.setImageDrawable(thumbdownCor);
    }

    public ListView getMyListViewComments() {
        return myListViewComments;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Details activity", "Resume" + "", null);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public EditText getEditTextComment() {
        return editTextComment;
    }

    public ImageButton getLikePostImgBtn() {
        return likePostImgBtn;
    }

    public ImageButton getDislikePostImgBtn() {
        return dislikePostImgBtn;
    }

    public TextView getTxtLike() {
        return txtLike;
    }

    public void setTxtLike(TextView txtLike) {
        this.txtLike = txtLike;
    }

    public TextView getTxtDislike() {
        return txtDislike;
    }

    public void setTxtDislike(TextView txtDislike) {
        this.txtDislike = txtDislike;
    }
}
