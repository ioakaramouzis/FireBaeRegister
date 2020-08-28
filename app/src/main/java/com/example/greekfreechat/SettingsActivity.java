package com.example.greekfreechat;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {


    private EditText mNameField,mPhoneField;

    private Button btnConfirm,btnBack;

    private ImageView ProfileImageView;

    private  String userId, phone, name, profileImageUrl;

    private Uri resultUri;

    private FirebaseAuth mAuth;

    private FirebaseDatabase mCostumerDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



       mNameField = (EditText)findViewById(R.id.name);
       mPhoneField = (EditText)findViewById(R.id.phone);
       ProfileImageView = (ImageView)findViewById(R.id.profileImage);
       btnConfirm =(Button)findViewById(R.id.confirm);
       btnBack = (Button)findViewById(R.id.back);


       mAuth = FirebaseAuth.getInstance();
       userId = mAuth.getCurrentUser().getUid();
    }
}