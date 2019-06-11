package com.application.paramountfinefoods;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.paramountfinefoods.utils.DominoBoldTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPwdActivity extends AppCompatActivity {

    private EditText passwordEmail;
    private DominoBoldTextView resetPassword, set_message;
    private FirebaseAuth firebaseAuth;
    TextInputLayout forgotemail;
    Typeface custom_font;


    private String newPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        custom_font = Typeface.createFromAsset(getAssets(), "Domine-Bold.ttf");

        passwordEmail = (EditText) findViewById(R.id.etPasswordEmail);
        resetPassword = (DominoBoldTextView) findViewById(R.id.tv_forgotpwd);
        firebaseAuth = FirebaseAuth.getInstance();
        forgotemail = (TextInputLayout) findViewById(R.id.forgotemail);

        set_message = (DominoBoldTextView) findViewById(R.id.showText);


        forgotemail.setTypeface(custom_font);
        passwordEmail.setTypeface(custom_font);
        resetPassword.setTypeface(custom_font);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = passwordEmail.getText().toString().trim();

                if (useremail.equals("")) {
                    Toast.makeText(ForgotPwdActivity.this, "Please enter your registered email ID", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                set_message.setVisibility(View.VISIBLE);
                                set_message.setText("Passowrd reset email sent");
                            } else {
                                set_message.setVisibility(View.VISIBLE);
                                //Toast.makeText(ForgotPwdActivity.this, "Error sending Password reset email",Toast.LENGTH_SHORT).show();
                                set_message.setText("Error sending Password reset email");
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(ForgotPwdActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
