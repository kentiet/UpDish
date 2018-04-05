package com.example.ken.updish.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ken.updish.Listener.RegisterListener;
import com.example.ken.updish.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);



        RegisterListener registerListener = new RegisterListener(this);
        Button btnReg = (Button) findViewById(R.id.btnRegisterAct);
        btnReg.setOnClickListener(registerListener);



    }

}
