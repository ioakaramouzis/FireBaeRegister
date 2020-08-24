package com.example.greekfreechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {



    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener fireBaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mAuth = FirebaseAuth.getInstance();
        fireBaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {
                    Intent intent = new Intent(Registration.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        final EditText emailTxt = (EditText)findViewById(R.id.email);
        final EditText passwordTxt = (EditText)findViewById(R.id.password);
        Button btnRegister = (Button)findViewById(R.id.egrafi);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final  String email = emailTxt.getText().toString();
                final  String password = passwordTxt.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(Registration.this,"sign up error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(fireBaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(fireBaseAuthListener);
    }
}