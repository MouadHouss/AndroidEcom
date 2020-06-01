package com.example.mye_commerceapplication.ui.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mye_commerceapplication.AddMarkerMapsActivity;
import com.example.mye_commerceapplication.HomeActivity;
import com.example.mye_commerceapplication.Model.VentesFireBase;
import com.example.mye_commerceapplication.Model.Ventes;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.example.mye_commerceapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerView_config_Commandes {
    private Context mcontext;
    private MyAdapter_Commands mVentesAdapter;



    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<Ventes> ventes, ArrayList<String> keys){
        mcontext=context;
        mVentesAdapter= new MyAdapter_Commands(ventes,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mVentesAdapter);
    }

    public class MyAdapter_Commands extends RecyclerView.Adapter<ViewHolderCommands> {
        private ArrayList<Ventes> ventesList;
        private ArrayList<String> mKeys;

        public MyAdapter_Commands(ArrayList<Ventes> data, ArrayList<String> mKeys) {
            this.ventesList = data;
            this.mKeys = mKeys;
        }

        @Override
        public ViewHolderCommands onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commandes_seller, parent, false);

            ViewHolderCommands viewHolder = new ViewHolderCommands(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderCommands holder, final int position) {
            try {
                holder.bind(ventesList.get(position),mKeys.get(position));
            } catch (IOException e) {
                e.printStackTrace();
            }

            holder.buttonLivrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String idVente = ventesList.get(position).getTelNumberClient()+ventesList.get(position).getProductId();
                    Log.i("venteString", "venteString "+ventesList.get(position).getTelNumberClient()+ventesList.get(position).getProductId());
                    Log.i("venteId", "venteID "+idVente);
                    if(ventesList.get(position).getStatus().equals("not shipped")){
                        new VentesFireBase().readVentes(new VentesFireBase.DataStatus() {
                            @Override
                            public void DataIsLoaded(ArrayList<Ventes> ventes, ArrayList<String> keys) {
                                for(Ventes vnt : ventes){
                                    String idVenteFor=vnt.getTelNumberClient()+vnt.getProductId();
                                    if (idVenteFor.equals(idVente)){

                                        Ventes vente=vnt;
                                        vente.setStatus("shipped");
                                        vente.setIdLignCommande(idVente);
                                        new VentesFireBase().addVente(vente);

                                    }

                                }
                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                        Toast toast=Toast.makeText(mcontext,
                                "Commande livrée",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        Navigation.findNavController((AppCompatActivity)v.getContext(),R.id.nav_host_fragment).navigate(R.id.nav_home);


                    }
                    else{
                        Intent mainIntent = new Intent(mcontext, AddMarkerMapsActivity.class);
                        mainIntent.putExtra("idVente",idVente);
                        mcontext.startActivity(mainIntent);
                        Navigation.findNavController((AppCompatActivity)v.getContext(),R.id.nav_host_fragment).navigate(R.id.nav_home);

                    }

                }
            });



        }

        @Override
        public int getItemCount() {
            return ventesList.size();

        }
    }

    public class ViewHolderCommands extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewNumBuyer;
        TextView textViewAdresseBuyer;
        TextView textViewDate;
        TextView textViewStatut;
        Button buttonLivrer;



        private String key;

        public ViewHolderCommands(View itemView)
        {
            super(itemView);
            this.imageView=(ImageView) itemView.findViewById(R.id.product_image_commande);
            this.textViewNumBuyer = (TextView) itemView.findViewById(R.id.num_buyer_command);
            this.textViewAdresseBuyer=(TextView) itemView.findViewById(R.id.adresse_buyer_command);
            this.textViewDate = (TextView) itemView.findViewById(R.id.date_command);
            this.textViewStatut = (TextView) itemView.findViewById(R.id.status_command);
            this.buttonLivrer = (Button) itemView.findViewById(R.id.buttonLivrer);
        }

        public void bind(Ventes vente, String key) throws IOException {
            Geocoder geocoder= new Geocoder(mcontext, Locale.getDefault());
            List<Address> addresses;
            try{
                addresses = geocoder.getFromLocation(Double.valueOf(vente.getLatitude()), Double.valueOf(vente.getLongitude()), 1);
                String address = addresses.get(0).getAddressLine(0);
                textViewAdresseBuyer.setText(address);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            textViewNumBuyer.setText(vente.getTelNumberClient());
            textViewDate.setText(vente.getDate());
            textViewStatut.setText(vente.getStatus());
            if(vente.getStatus().equals("not shipped")){
                imageView.setImageResource(R.drawable.notdelivered);
                buttonLivrer.setText("Livrer?");

            }
            else{
                imageView.setImageResource(R.drawable.delivered);
                buttonLivrer.setText("Commande déja livrée");

            }
            //Picasso.get().load(produit.getImage()).into(imageView);


            this.key=key;
        }
    }
}
