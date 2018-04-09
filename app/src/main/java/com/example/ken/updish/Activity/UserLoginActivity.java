/*
    This activity is for the login screen
 */

package com.example.ken.updish.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.ken.updish.Listener.LoginListener;
import com.example.ken.updish.R;
import com.example.ken.updish.Utility.SharedResources;

public class UserLoginActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Set useful variables for the application
        setUsefulResources();

        // Declare
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnReg = (Button) findViewById(R.id.btnRegister);

        //Set listener
        LoginListener oll = new LoginListener(this);
        btnLogin.setOnClickListener(oll);

        // Set useful variables for the application
        setUsefulResources();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, UserRegActivity.class));
            }
        });

        //Hard code username password for dev
//        EditText usernameLogin = (EditText)findViewById(R.id.editTextUserNameLogin);
//        EditText passwordLogin = (EditText)findViewById(R.id.editTextPassword);
//        usernameLogin.setText("tan");
//        passwordLogin.setText("tan");
    }

    private void setUsefulResources()
    {
        String host = "10.0.2.2";
//        String host = "10.1.122.27:8888";

        SharedResources sr = SharedResources.getInstance();
        sr.addStringValue(this, "appName", "Updish");
        sr.addStringValue(this, "loginUrl","http://" + host + "/updish/api/v1/login");
        sr.addStringValue(this, "postUrl", "http://" + host + "/updish/api/v1/posts");
        sr.addStringValue(this, "detailsUrl", "http://" + host + "/updish/api/v1/post");
        sr.addStringValue(this, "commentUrl", "http://" + host + "/updish/api/v1/comment");
        sr.addStringValue(this, "likeUrl", "http://" + host + "/updish/api/v1/like");
        sr.addStringValue(this, "postnew", "http://" + host + "/updish/api/v1/newpost");
        sr.addStringValue(this, "regUrl", "http://" + host + "/updish/api/v1/register");
        sr.addStringValue(this, "featureUrl", "http://" + host + "/updish/api/v1/feature");

        sr.addStringValue(this, "GoogleMapLocation", "N/A"); // Not set for google map location
        sr.addStringValue(this, "GoogleMapName", "N/A"); // Not set for google map name
        sr.addStringValue(this, "selectedLong", "N/A"); // Not set Long
        sr.addStringValue(this, "selectedLat", "N/A"); // Not set for Lat



    }
}