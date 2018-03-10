package com.example.ken.updish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ken.updish.Utility.BackgroundWorker;

public class TestLoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);

        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);

//        btnLogin = (Button)findViewById(R.id.btnLogin);

    }

    public void onLogin(View view)
    {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String type = "login";

        BackgroundWorker bgWorker = new BackgroundWorker(this);
        bgWorker.execute(type, username, password);
    }
}
