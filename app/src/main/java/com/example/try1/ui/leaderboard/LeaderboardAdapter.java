package com.example.try1.ui.leaderboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try1.MainActivity;
import com.example.try1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    ArrayList<LeaderboardEntry> users;
    Context currentContext;

    public LeaderboardAdapter(Context context, ArrayList<LeaderboardEntry> list) {
        currentContext = context;
        users = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvRank, tvUserName, tvScore;
        ImageView ivMedal, ivProfilePic;
        CardView cvLeaderboardItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRank = itemView.findViewById(R.id.tvRank);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvScore = itemView.findViewById(R.id.tvScore);
            ivMedal = itemView.findViewById(R.id.ivMedal);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            cvLeaderboardItem = itemView.findViewById(R.id.cvLeaderboardItem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_leaderboard_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setTag(users.get(position));
        holder.tvScore.setText("Score: " + Integer.toString(users.get(position).getLocationsVisited()));
        holder.tvUserName.setText(users.get(position).getUserName());
        holder.tvRank.setText("Rank " + Integer.toString(position + 1));

        if(users.get(position).getUserName().equals(MainActivity.userName)) {
            holder.cvLeaderboardItem.setCardBackgroundColor(R.color.colorAccent);
        }


        switch (position) {
            case 0:
                holder.ivMedal.setVisibility(View.VISIBLE);
                holder.ivMedal.setImageResource(R.drawable.medal);
                break;
            case 1:
                holder.ivMedal.setVisibility(View.VISIBLE);
                holder.ivMedal.setImageResource(R.drawable.medal2);
                break;
            case 2:
                holder.ivMedal.setVisibility(View.VISIBLE);
                holder.ivMedal.setImageResource(R.drawable.medal3);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
