package com.example.greekfreechat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

class MatchesViewHolders  extends  RecyclerView.ViewHolder implements View.OnClickListener{
  public TextView mMatchId,mMatchName;
  public ImageView mMatchImage;

    public MatchesViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId = (TextView) itemView.findViewById(R.id.matchId);
        mMatchName = (TextView) itemView.findViewById(R.id.matchName);
        mMatchImage = (ImageView) itemView.findViewById(R.id.matchImage);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(view.getContext(),ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("matchId",mMatchId.getText().toString());        //na valw to onoma an thelw na emfanizete to onoma kai oxi to id
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}
