package com.example.mye_commerceapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mye_commerceapplication.Model.Product;
import com.example.mye_commerceapplication.Model.Shopping;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {

    public Context context;
    public ArrayList<Shopping> list;
    DatabaseReference mRefProduct;
    private Product product;

    public SalesAdapter(Context context, ArrayList<Shopping> list) {
        this.context = context;
        this.list = list;
        mRefProduct= FirebaseDatabase.getInstance().getReference("Products");
    }

    @Override
    public void onBindViewHolder(@NonNull SalesAdapter.ViewHolder holder, int position) {

        for (Product p:SalesActivity.list_produits_cart_activity){
            if(p.getPid().equals(list.get(position).getProductId())){
                product = p;
                break;
            }
        }

        holder.mProductName.setText("name : "+product.getPname());
        holder.mProductQuantity.setText("quantity : "+list.get(position).getQuantity());
        holder.mProductPrice.setText(product.getPrice()+" MAD");
        Picasso.get().load(product.getImage()).into(holder.mProductImage);
        holder.mProductDescription.setText("descriptiion : "+product.getDescription());

        if(list.get(position).getStatus().equals("not shipped")){
            holder.mButtonStatus.setBackgroundColor(Color.rgb(233,88,16));
        }

        else{
            holder.mButtonStatus.setBackgroundColor(Color.GREEN);
        }
        holder.mButtonStatus.setText(list.get(position).getStatus());

    }

    @NonNull
    @Override
    public SalesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sale_view= LayoutInflater.from(context).inflate(R.layout.vente_item,parent,false);
        return new SalesAdapter.ViewHolder(sale_view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mProductName;
        private ImageView mProductImage;
        private TextView mProductPrice;
        private TextView mProductDescription;
        private TextView mProductQuantity;
        private Button mButtonStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductName = itemView.findViewById(R.id.shopping_product_name);
            mProductImage = itemView.findViewById(R.id.shopping_product_image);
            mProductPrice = itemView.findViewById(R.id.shopping_product_price);
            mProductDescription = itemView.findViewById(R.id.shopping_product_description);
            mProductQuantity = itemView.findViewById(R.id.shopping_product_quantity);
            mButtonStatus=itemView.findViewById(R.id.shopping_status_btn);

        }

    }
}