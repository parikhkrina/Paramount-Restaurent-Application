package com.application.paramountfinefoods;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.paramountfinefoods.utils.DominoBoldTextView;
import com.application.paramountfinefoods.utils.SharedPreferenceHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText et_username, et_pwd;
    DominoBoldTextView txt_login, tv_fgt_pwd, tv_register;
    TextInputLayout input_email, input_pwd;

    Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        init();
        setListeners();
        mAuth = FirebaseAuth.getInstance();
    }

    private void init() {

        custom_font = Typeface.createFromAsset(getAssets(), "Domine-Bold.ttf");

        et_username = (EditText) findViewById(R.id.et_email);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        txt_login = (DominoBoldTextView) findViewById(R.id.tv_login);
        tv_register = (DominoBoldTextView) findViewById(R.id.tv_register);
        tv_fgt_pwd = (DominoBoldTextView) findViewById(R.id.fgt_pwd);

        input_email = (TextInputLayout) findViewById(R.id.input_email);
        input_pwd = (TextInputLayout) findViewById(R.id.input_password);


        // custom_font = Typeface.createFromAsset(getAssets(), "DomineBold.ttf");


        input_email.setTypeface(custom_font);
        input_pwd.setTypeface(custom_font);
        et_pwd.setTypeface(custom_font);
        et_username.setTypeface(custom_font);

    }

    private void setListeners() {


        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String name = et_username.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(name, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(), "user_id", name);
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });


        // register
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        tv_fgt_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPwdActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
