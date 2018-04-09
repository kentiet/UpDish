/*
    This Listener is fired when user clicks on the register button
 */
package com.example.ken.updish.Listener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.ken.updish.BackgroundWorker.RegisterBackgroundWork;
import com.example.ken.updish.R;
import com.example.ken.updish.R.layout;

/**
 * Created by Ken on 2018-04-04.
 */

public class RegisterListener implements View.OnClickListener {

    Activity context;
    EditText username,email, psOne, psTwo;
    String uName, uEmail, uPwd1, uPwd2, title;

    public RegisterListener(Activity _context) {
        this.context = _context;
    }

    @Override
    public void onClick(View v) {
        username = (EditText) context.findViewById(R.id.editTextUserNameReg);
        email = (EditText) context.findViewById(R.id.editTextEmail);
        psOne = (EditText) context.findViewById(R.id.editTextPasswordReg1);
        psTwo = (EditText) context.findViewById(R.id.editTextPasswordReg2);

        this.uName = username.getText().toString();
        this.uEmail = email.getText().toString();
        this.uPwd1 = psOne.getText().toString();
        this.uPwd2 = psTwo.getText().toString();
        this.title = "Registration Status";

        if(isPwdMatch(uPwd1, uPwd2)) {
            Log.e("Clicked Listener", "reg lis");
            RegisterBackgroundWork mRegBg = new RegisterBackgroundWork(context);
            mRegBg.execute(title, uName, uEmail, uPwd1);
        } else {
            Toast.makeText(context,"Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPwdMatch(String pwd1 , String pwd2) {
        return pwd1.equals(pwd2);
    }

}

