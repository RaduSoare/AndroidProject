package com.example.try1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    ArrayList<Location> locations;
    Context currentContext;

    public LocationAdapter(Context context, ArrayList<Location> list) {

        locations = list;
        currentContext = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLocationName, tvLocationSpecific, tvLocationAdress;
        ImageView ivLocationThumbnail;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvLocationAdress = itemView.findViewById(R.id.tvLocationAdress);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            tvLocationSpecific = itemView.findViewById(R.id.tvLocationSpecific);
            ivLocationThumbnail = itemView.findViewById(R.id.ivLocationThumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //activity.onItemClicked(locations.indexOf(view.getTag()));
                    Intent intent = new Intent(currentContext, com.example.try1.ui.general.DetailActivity.class);
                    intent.putExtra("description", ApplicationClass.restaurants.get(locations.indexOf(view.getTag())).getDescription());
                    currentContext.startActivity(intent);
                }
            });
            
            tvLocationAdress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + tvLocationAdress.getText().toString()));
                    currentContext.startActivity(intent);
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

        Glide.with(holder.itemView)
                .load(locations.get(position).getThumbnailLink())
                .into(holder.ivLocationThumbnail);


    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
