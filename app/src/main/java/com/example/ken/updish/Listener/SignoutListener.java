package com.example.ken.updish.Listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;

import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.Activity.UserLoginActivity;
import com.example.ken.updish.Database.DatabaseHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tanthinh on 4/8/18.
 */

public class SignoutListener implements View.OnClickListener {

    Activity context;
    AlertDialog alertDialog;

    public SignoutListener(Activity con) {
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Sign out");
        alertDialog.setMessage("Signing out...");
        alertDialog.show();

        // Reset user

        TimerTask tsk = new TimerTask(){
            @Override
            public void run() {
                DatabaseHelper.getInstance().setCurrentUser("", "", new String[10]);
                Intent intent = new Intent(context, UserLoginActivity.class);
                context.startActivity(intent);
                context.finish();
                alertDialog.dismiss();
            }
        };
        Timer opening = new Timer();
        opening.schedule(tsk, 1500);

    }

}
