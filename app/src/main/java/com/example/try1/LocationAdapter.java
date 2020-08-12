package com.example.try1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    ItemClicked activity;
    ArrayList<Location> locations;

    public LocationAdapter(Context context, ArrayList<Location> list) {

        activity = (ItemClicked) context;
        locations = list;
    }

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLocationName, tvLocationSpecific, tvLocationAdress;
        ImageView ivLocationThumbnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationAdress = itemView.findViewById(R.id.tvLocationAdress);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            tvLocationSpecific = itemView.findViewById(R.id.tvLocationSpecific);
            ivLocationThumbnail = itemView.findViewById(R.id.ivLocationThumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onItemClicked(locations.indexOf(view.getTag()));
                }
            });
        }

    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(locations.get(position));

        holder.tvLocationSpecific.setText(locations.get(position).getLocationSpecific());
        holder.tvLocationName.setText(locations.get(position).getLocationName());
        holder.tvLocationAdress.setText(locations.get(position).getLocationAdress());
        holder.ivLocationThumbnail.setImageResource(locations.get(position).getThumbnailID());






    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
