package com.example.greekfreechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {



    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener fireBaseAuthListener;
    FirebaseDatabase rootNode;
    DatabaseReference DbReference;


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
        final EditText nameTxt = (EditText)findViewById(R.id.name);
        final RadioButton buttonMale = (RadioButton) findViewById(R.id.Male);
        final RadioButton buttonFemale = (RadioButton) findViewById(R.id.Female);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                   final  String email = emailTxt.getText().toString();
                 final  String password = passwordTxt.getText().toString();
                final String male = buttonMale.getText().toString().trim();
                final String female = buttonFemale.toString().trim();
                final String name = nameTxt.getText().toString().trim();

                  mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {

                        if (buttonMale.isChecked()) {
                            Toast.makeText(Registration.this, "ok male", Toast.LENGTH_SHORT).show();
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                               Map userInfo = new HashMap<>();
                               userInfo.put("name", name);
                               userInfo.put("sex","male");
                            userInfo.put("profileImageUrl", "default");
                            currentUserDb.updateChildren(userInfo);
                        }
                        else {
                            Toast.makeText(Registration.this, "ok female", Toast.LENGTH_SHORT).show();
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                            Map userInfo = new HashMap<>();
                            userInfo.put("name", name);
                            userInfo.put("sex","female");
                            userInfo.put("profileImageUrl", "default");
                            currentUserDb.updateChildren(userInfo);
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


