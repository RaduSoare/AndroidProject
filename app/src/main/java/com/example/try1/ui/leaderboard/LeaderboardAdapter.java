package com.example.try1.ui.leaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try1.R;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRank = itemView.findViewById(R.id.tvRank);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_leaderboard_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setTag(users.get(position));
        holder.tvScore.setText(Integer.toString(users.get(position).getLocationsVisited()));
        holder.tvUserName.setText(users.get(position).getUserName());
        holder.tvRank.setText(Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
