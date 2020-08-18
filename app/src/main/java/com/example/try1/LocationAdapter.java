package com.example.try1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.operation.Merge;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    ArrayList<Location> locations;
    Context currentContext;

    // ID user curent
    String userID;
    // referinta la pagina din Firestore a userului curent
    DocumentReference firestoreReference;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    ArrayList<String> locationsVisited;


    public LocationAdapter(Context context, ArrayList<Location> list) {

        locations = list;
        currentContext = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLocationName, tvLocationSpecific, tvLocationAdress;
        ImageView ivLocationThumbnail, ivVisited;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);


            // Instantieri componente din itemView, practic componentele unui element din lista de locatii
            tvLocationAdress = itemView.findViewById(R.id.tvLocationAdress);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            tvLocationSpecific = itemView.findViewById(R.id.tvLocationSpecific);
            ivLocationThumbnail = itemView.findViewById(R.id.ivLocationThumbnail);
            ivVisited = itemView.findViewById(R.id.ivVisited);

            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();

            // Retine ID-ul userului curent
            userID = firebaseAuth.getCurrentUser().getUid();

            // Retine documentul corespunzator ID-ului
            firestoreReference = firebaseFirestore.collection(currentContext.getString(R.string.users_collection)).document(userID);


            // Logica click pe un element din lista, trimitere catre activitatea de descriere a locatiei
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(currentContext, com.example.try1.ui.general.DetailActivity.class);
                    intent.putExtra(currentContext.getString(R.string.description_location_field), ApplicationClass.restaurants.get(locations.indexOf(view.getTag())).getDescription());
                    currentContext.startActivity(intent);
                }
            });

            // Logica click pe adresa unei locatii, pornire Maps cu pin pe locatie
            tvLocationAdress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentContext.getString(R.string.location_hack) + tvLocationAdress.getText().toString()));
                    currentContext.startActivity(intent);
                }
            });

            ivVisited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                     Verifica daca locatia e deja vizitata, daca nu este, afiseaza prompt-ul pentru
                     a confirma ca userul vrea sa o marcheze ca vizitata
                     */
                    if((int) ivVisited.getTag() == R.drawable.not_visited) {
                        showMarkAsVisitedDialog(itemView, ivVisited);
                    }
                }
            });
        }

    }
    public void showMarkAsVisitedDialog(final View view, final ImageView ivVisited) {

        AlertDialog.Builder userOption = new AlertDialog.Builder(currentContext);
        userOption.setMessage("Do you want to mark this location as visited?");
        userOption.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                    ivVisited.setImageResource(R.drawable.visited);
                    ivVisited.setTag(R.drawable.visited);
                    // Adauga locatia in Map-ul de locatii vizitate de pe local
                    MainActivity.locationsVisited.put(ApplicationClass.restaurants.get(locations.indexOf(view.getTag())).getLocationName(), true);
                    // Adauga locatia in Map-ul de locatii vizitate din Firestore
                    firestoreReference.update("visited."+ApplicationClass.restaurants.get(locations.indexOf(view.getTag())).getLocationName(), true);


            }
        });
        userOption.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*
                Do nothing
                 */
            }
        });
        userOption.create().show();


    }

    /*
    Cred ca asta construieste felul in care arata RecycleView-ul
     */
    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    /*
    Seteaza contentul din fiecare componenta din lista
     */
    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, final int position) {
        holder.itemView.setTag(locations.get(position));

        holder.tvLocationSpecific.setText(locations.get(position).getLocationSpecific());
        holder.tvLocationName.setText(locations.get(position).getLocationName());
        holder.tvLocationAdress.setText(locations.get(position).getLocationAdress());
        holder.ivVisited.setTag(R.drawable.not_visited);
        Glide.with(holder.itemView)
                .load(locations.get(position).getThumbnailLink())
                .into(holder.ivLocationThumbnail);

        /*
        Verifica daca locatia apare deja vizitata in Firestore
         */
        if (MainActivity.locationsVisited.containsKey(locations.get(position).getLocationName()) == true) {
            holder.ivVisited.setImageResource(R.drawable.visited);
            holder.ivVisited.setTag(R.drawable.visited);
        }
    }


    @Override
    public int getItemCount() {
        return locations.size();
    }
}
