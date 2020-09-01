package com.example.greekfreechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private  RecyclerView.Adapter  mMatchesAdapter;
    private  RecyclerView.LayoutManager mMatchesLayoutManager;
    private  String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);

        mMatchesLayoutManager = new LinearLayoutManager(MatchesActivity.this);
        mRecyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter = new MatchesAdapter(getDataSetMatches(), MatchesActivity.this);
        mRecyclerView.setAdapter(mMatchesAdapter);

          getUserMatchId();




    }

    private void getUserMatchId()

    {

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("connections").child("matches");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())


                {
                    for(DataSnapshot match: snapshot.getChildren())
                    {
                            fetchMatchInformation(match.getKey());
                    }

                }
            }

            private void fetchMatchInformation(String key)
            {

                DatabaseReference UserDb= FirebaseDatabase.getInstance().getReference().child("Users").child(key);
                UserDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String userId = snapshot.getKey();
                            String name ="";
                            String profileImageUrl ="";
                            if (snapshot.child("name").getValue() != null)

                            {
                                name = snapshot.child("name").getValue().toString();

                            }
                            if (snapshot.child("profileImageUrl").getValue() != null)

                            {
                                profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();

                            }
                            MatchesObjects obj = new MatchesObjects(userId, name , profileImageUrl);
                            ResultMatches.add(obj);
                            mMatchesAdapter.notifyDataSetChanged();

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

            });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private ArrayList<MatchesObjects> ResultMatches = new ArrayList <MatchesObjects>();
    private List<MatchesObjects> getDataSetMatches() {
        return  ResultMatches;


    }
}