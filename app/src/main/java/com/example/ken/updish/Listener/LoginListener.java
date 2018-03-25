package com.example.ken.updish.Listener;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ken.updish.R;
import com.example.ken.updish.Utility.LoginBackgroundWorker;

/**
 * Created by tanthinh on 3/25/18.
 */

public class LoginListener implements View.OnClickListener {

    // Constructor
    Activity context;
    EditText editTextUsername, editTextPassword;
    String username, password, title;

    public LoginListener(Activity context)
    {
        this.context = context;

    }

    @Override
    public void onClick(View v) {
        editTextUsername = (EditText)context.findViewById(R.id.editTextUserNameLogin);
        editTextPassword = (EditText)context.findViewById(R.id.editTextPassword);

        this.username = editTextUsername.getText().toString();
        this.password = editTextPassword.getText().toString();
        this.title = "Login Status";

        LoginBackgroundWorker bgWorker = new LoginBackgroundWorker(context);
        bgWorker.execute(title, username, password);
    }

}
