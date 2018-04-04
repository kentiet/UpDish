package com.example.ken.updish.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.ken.updish.Listener.LoginListener;
import com.example.ken.updish.R;
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
    }
}