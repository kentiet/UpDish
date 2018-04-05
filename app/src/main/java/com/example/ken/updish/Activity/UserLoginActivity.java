package com.example.ken.updish.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.ken.updish.Listener.LoginListener;
import com.example.ken.updish.R;
import com.example.ken.updish.Utility.SharedResources;

public class UserLoginActivity extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Declare
        btnLogin = (Button)findViewById(R.id.btnLogin);

        //Set listener
        LoginListener oll = new LoginListener(this);
        btnLogin.setOnClickListener(oll);

        // Set useful variables for the application
        setUsefulResources();

        //Hard code username password for dev
        EditText usernameLogin = (EditText)findViewById(R.id.editTextUserNameLogin);
        EditText passwordLogin = (EditText)findViewById(R.id.editTextPassword);
        usernameLogin.setText("tan");
        passwordLogin.setText("tan");
    }

    private void setUsefulResources()
    {
        SharedResources sr = SharedResources.getInstance();
        sr.addStringValue(this, "appName", "Updish");
        sr.addStringValue(this, "loginUrl","http://10.0.2.2:8888/updish/api/v1/login");
        sr.addStringValue(this, "postUrl", "http://10.0.2.2:8888/updish/api/v1/posts");
        sr.addStringValue(this, "detailsUrl", "http://10.0.2.2:8888/updish/api/v1/post");
        sr.addStringValue(this, "commentUrl", "http://10.0.2.2:8888/updish/api/v1/comment");
        sr.addStringValue(this, "likeUrl", "http://10.0.2.2:8888/updish/api/v1/like");
    }
}
