package com.example.greekfreechat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MatchesViewHolders  extends  RecyclerView.ViewHolder implements View.OnClickListener{
  public TextView mMatchId;

    public MatchesViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId = (TextView) itemView.findViewById(R.id.matchId);

    }

    @Override
    public void onClick(View view) {

    }
}
