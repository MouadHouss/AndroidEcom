package com.example.mye_commerceapplication.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mye_commerceapplication.R;
import com.example.mye_commerceapplication.Model.Products;
import com.example.mye_commerceapplication.SellerMainActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerView_config{
    private Context mcontext;
    private MyAdapter mProductsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<Products> produits,ArrayList<String> keys){
        Log.i("RecyclerViewSetConfig", "setConfig: ");
        mcontext=context;
        mProductsAdapter= new MyAdapter(produits,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mProductsAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<Products> produitsList;
        private ArrayList<String> mKeys;

        public MyAdapter(ArrayList<Products> data,ArrayList<String> mKeys) {
            this.produitsList = data;
            this.mKeys=mKeys;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position)
        {
            holder.bind(produitsList.get(position),mKeys.get(position));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id=produitsList.get(position).getPid();

                    Bundle bundle = new Bundle();
                    bundle.putString("idProduct", id);

                    ModProductsFragment modProductsFragment=new ModProductsFragment();
                    modProductsFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    //activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_mod,modProductsFragment).addToBackStack(null).commit();
                    Navigation.findNavController(activity,R.id.nav_host_fragment).navigate(R.id.action_nav_gallery_to_nav_mod,bundle);




                }
            });

        }

        @Override
        public int getItemCount()
        {
            return produitsList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewDescription;
        TextView textViewPrice;
        private String key;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.imageView=(ImageView) itemView.findViewById(R.id.product_image);
            this.textViewName= (TextView) itemView.findViewById(R.id.product_name);
            this.textViewDescription = (TextView) itemView.findViewById(R.id.product_description);
            this.textViewPrice = (TextView) itemView.findViewById(R.id.product_price);
        }

        public void bind(Products produit,String key){
            textViewName.setText(produit.getPname());
            textViewPrice.setText(produit.getPrice());
            textViewDescription.setText(produit.getDescription());
            Picasso.get().load(produit.getImage()).into(imageView);


            this.key=key;
        }


    }


}
