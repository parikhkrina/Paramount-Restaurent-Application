package com.application.paramountfinefoods;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.paramountfinefoods.utils.DominoBoldTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;



public class RegisterActivity extends AppCompatActivity {


    Typeface custom_font;
    private FirebaseAuth mAuth;
    // Decalaration
    EditText et_username, et_phoneNumber, et_pwd, et_email;
    DominoBoldTextView tv_login, tv_register;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, inputLayoutMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        setListeners();
        mAuth = FirebaseAuth.getInstance();
    }

    private void init() {

        custom_font = Typeface.createFromAsset(getAssets(), "Domine-Bold.ttf");
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_userphone);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        //iniitialization
        et_username = (EditText) findViewById(R.id.et_uname);
        et_phoneNumber = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        tv_register = (DominoBoldTextView) findViewById(R.id.tv_register);
        tv_login = (DominoBoldTextView) findViewById(R.id.tv_sign_in);


        inputLayoutName.setTypeface(custom_font);
        inputLayoutEmail.setTypeface(custom_font);
        inputLayoutMobile.setTypeface(custom_font);
        inputLayoutPassword.setTypeface(custom_font);

        et_username.setTypeface(custom_font);
        et_phoneNumber.setTypeface(custom_font);
        et_email.setTypeface(custom_font);
        et_pwd.setTypeface(custom_font);

    }


    private void setListeners() {

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();


        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registerUser();

            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private void registerUser() {

        final String name = et_username.getText().toString().trim();
        final String loc_id = et_phoneNumber.getText().toString();
        final String email = et_email.getText().toString().trim();
        final String password = et_pwd.getText().toString().trim();


        if (name.isEmpty()) {
            et_username.setError("name required");
            et_username.requestFocus();
            return;
        }

        if (loc_id.isEmpty()) {
            et_phoneNumber.setError("phone required");
            et_phoneNumber.requestFocus();
            return;
        }

//        if (loc_id.length() != 10) {
//            et_phoneNumber.setError("10 Number is required");
//            et_phoneNumber.requestFocus();
//            return;
//        }

        if (TextUtils.isEmpty(email)) {
            et_email.setError("email required");
            et_email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            et_pwd.setError("email required");
            et_pwd.requestFocus();
            return;
        }

        if (password.length() < 6) {
            et_pwd.setError("Password too short, enter minimum 6 characters!");
            et_pwd.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // register extra field in the database

                            final User user = new User(name, loc_id, email);

                            // create user in the firebase


                            FirebaseDatabase.getInstance().getReference("Managers").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "Registration Sucessfull", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                    } else {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                            Toast.makeText(getApplicationContext(), "Manager is already registered", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        if (mAuth.getCurrentUser() != null) {

        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

}
