package com.example.greekfreechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

class MatchesAdapter  extends RecyclerView.Adapter<MatchesViewHolders> {

 private List <MatchesObjects> matchesList;
 private Context context;


 public  MatchesAdapter(List<MatchesObjects> matchesList,Context context)
 {
    this.matchesList = matchesList;
    this.context = context;

 }

    @NonNull
    @Override
    public MatchesViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_matches, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolders rcv = new MatchesViewHolders(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolders holder, int position) {

        holder.mMatchId.setText(matchesList.get(position).GetUserId());
        holder.mMatchName.setText(matchesList.get(position).GetName());
        if (!matchesList.get(position).GetProfileImageUrl().equals("default")) {
            Glide.with(context).load(matchesList.get(position).GetProfileImageUrl()).into(holder.mMatchImage);


        }
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
