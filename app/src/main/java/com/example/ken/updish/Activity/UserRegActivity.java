package com.example.ken.updish.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ken.updish.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegActivity extends AppCompatActivity {

//    private String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
//    private String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    private EditText regUser;
    private EditText regPwdOne;
    private EditText regPwdTwo;
    private String userName;
    private String pwdOne;
    private String pwdTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);

        regUser = (EditText) findViewById(R.id.editTextUserNameReg);
        regPwdOne = (EditText) findViewById(R.id.editTextPasswordReg1);
        regPwdTwo = (EditText) findViewById(R.id.editTextPasswordReg2);



        Button btnReg = (Button) findViewById(R.id.btnRegisterAct);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = regUser.getText().toString();
                pwdOne = regPwdOne.getText().toString();
                pwdTwo = regPwdTwo.getText().toString();

                if(isPwdMatch(pwdOne, pwdTwo)) {
                    Log.e("pwd" , "matched");
                }
            }
        });



    }
//    private boolean isValidUserPwd(String pwd1, String pwd2, String u) {
//        return isValidUsername(u) && isValidPassword(pwd1) && isValidPassword(pwd2);
//    }

    private boolean isPwdMatch(String pwd1 , String pwd2) {
        return pwd1.equals(pwd2);
    }

//    private boolean isValidPassword(String pwd) {
//        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
//        Matcher matcher = pattern.matcher(pwd);
//
//        return matcher.matches();
//    }

//    private boolean isValidUsername(String u) {
//        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
//        Matcher matcher = pattern.matcher(u);
//
//        return matcher.matches();
//    }
}
