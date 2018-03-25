package com.example.ken.updish.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.ken.updish.Listener.OnLoginListener;
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
        OnLoginListener oll = new OnLoginListener(this);
        btnLogin.setOnClickListener(oll);
    }
}
