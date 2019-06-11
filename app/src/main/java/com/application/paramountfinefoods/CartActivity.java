package com.application.paramountfinefoods;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CartActivity extends AppCompatActivity {

    TextView tv_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tv_confirm= findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,HomeActivity.class);
                intent.putExtra("confirmorders",true);
                startActivity(intent);

            }
        });
    }
}
