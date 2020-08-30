package com.example.greekfreechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {


    private EditText mNameField,mPhoneField;

    private Button btnConfirm,btnBack;

    private ImageView ProfileImageView;

    private  String userId, phone, name, profileImageUrl,UserSex;

    private Uri resultUri;

    private FirebaseAuth mAuth;

    private DatabaseReference mCostumerDatabase;



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

       mCostumerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
       getUserInfo();



       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
               return;
           }
       });

       ProfileImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
               startActivityForResult(intent,1);
           }
       });

       btnConfirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)

           {
               saveUserInformation();



           }
       });
    }



    private void getUserInfo() {
        mCostumerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("name") != null) {
                        name = map.get("name").toString();
                        mNameField.setText(name);
                    }
                    if (map.get("phone") != null) {
                        phone = map.get("phone").toString();
                        mPhoneField.setText(phone);
                    }
                    if (map.get("sex") != null) {
                        UserSex = map.get("sex").toString();


                        if (map.get("profileImageUrl") != null) {
                            profileImageUrl = map.get("profileImageUrl").toString();  //allagh
                            switch (profileImageUrl) {

                                case "default":
                                    Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(ProfileImageView);
                                default:
                                    Glide.with(getApplication()).load(profileImageUrl).into(ProfileImageView);
                                    break;
                            }


                        }
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveUserInformation() {
        name = mNameField.getText().toString();
        phone = mPhoneField.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
        mCostumerDatabase.updateChildren(userInfo);
        if (resultUri != null) {

            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String photolink =uri.toString();
                            Map userInfo = new HashMap();
                            userInfo.put("profileImageUrl", photolink);
                            mCostumerDatabase.updateChildren(userInfo);

                            finish();
                            return;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            finish();
                            return;
                        }
                    });
                }
            });
                }

            }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1 && resultCode == RESULT_OK)
        {
            final  Uri imageUri = data.getData();
            resultUri = imageUri;
            ProfileImageView.setImageURI(resultUri);
        }
    }
}