package com.example.ken.updish.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ken.updish.Listener.LoginListener;
import com.example.ken.updish.R;

public class UserLoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Declare
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnReg = (Button) findViewById(R.id.btnRegister);

        //Set listener
        LoginListener oll = new LoginListener(this);
        btnLogin.setOnClickListener(oll);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, UserRegActivity.class));
            }
        });
    }
}
