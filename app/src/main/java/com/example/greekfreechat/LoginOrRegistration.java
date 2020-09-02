package com.example.greekfreechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginOrRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_registration);

        Button login = (Button)findViewById(R.id.login);
        Button register = (Button)findViewById(R.id.register1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LoginOrRegistration.this,Login.class);
                startActivity(intent);
                finish();
                return;

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LoginOrRegistration.this,Registration.class);
                startActivity(intent);
                finish();
                return;

            }
        });


    }
}