package com.example.ken.updish.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.ken.updish.Listener.LoginListener;
import com.example.ken.updish.R;
import com.example.ken.updish.Utility.SharedResources;

public class UserLoginActivity extends AppCompatActivity {
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Set useful variables for the application
        setUsefulResources();

        // Declare
        btnLogin = (Button)findViewById(R.id.btnLogin);

        //Set listener
        LoginListener oll = new LoginListener(this);
        btnLogin.setOnClickListener(oll);
    }

    private void setUsefulResources()
    {
        String localip = "140.161.82.9";
        SharedResources sr = SharedResources.getInstance();
        sr.addStringValue(this, "appName", "Updish");
        sr.addStringValue(this, "loginUrl","http://10.0.2.2/updish/api/v1/login");
        sr.addStringValue(this, "postUrl", "http://10.0.2.2/updish/api/v1/posts");
        sr.addStringValue(this, "commentUrl", "http://10.0.2.2/updish/api/v1/comment");
        sr.addStringValue(this, "likeUrl", "http://10.0.2.2/updish/api/v1/like");

        Log.e("Test Sr: ", sr.getStringValue(this, "loginUrl"));
    }
}