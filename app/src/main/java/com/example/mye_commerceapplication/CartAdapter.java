package com.example.mye_commerceapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mye_commerceapplication.Listeners.OnSwipeTouchListener;
import com.example.mye_commerceapplication.Model.LigneCommande;
import com.example.mye_commerceapplication.Model.Product;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public ArrayList<LigneCommande> list_commandes;
    public  Context context;
    private Product product;
    public static int totalPrice=0;
    private DatabaseReference mRef,mRefProduct;
    private GestureDetectorCompat mDetector;

    public CartAdapter(){

    }

    public CartAdapter(Context context,ArrayList<LigneCommande> list_commandes){
            this.context=context;
            this.list_commandes=list_commandes;
            mRefProduct=FirebaseDatabase.getInstance().getReference("Products");
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lignecommande_view= LayoutInflater.from(context).inflate(R.layout.cart_items_layout,parent,false);
        return new ViewHolder(lignecommande_view);//,mOnProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, final int position) {

        for (Product p:CartActivity.list_produits_cart_activity){
            if(p.getPid().equals(list_commandes.get(position).getProductId())){
                product  = p;
                break;
            }
        }

        holder.productName.setText("name : "+product.getPname());
        holder.productQuantity.setText("quantity: "+list_commandes.get(position).getQuantity());
        holder.productPrice.setText("unit price: "+product.getPrice()+" MAD");
        totalPrice+=Integer.valueOf(product.getPrice())*Integer.valueOf(list_commandes.get(position).getQuantity());


        holder.itemView.setOnTouchListener(new OnSwipeTouchListener(context){

            public void onSwipeRight() {
                //Toast.makeText(context, "product id in ligneCommande:"+list_commandes.get(position).getProductId(), Toast.LENGTH_SHORT).show();
                mRef=FirebaseDatabase.getInstance().getReference();
                mRef.child("ligneCommande")
                        .child(Prevalent.currentOnlineUser.getPhone())
                        .child(list_commandes.get(position).getIdLignCommande())
                        .setValue(null);//!!!!!!

                //Update the value of number of Floating Button
                Prevalent.numberOfCommands--;
                Prevalent.numberOfCommandText.setText(String.valueOf(Prevalent.numberOfCommands));
                //Toast.makeText(context, "root id is: "+ mRef.child("ligneCommande")
                        //.child(Prevalent.currentOnlineUser.getPhone())
                        //.child(list_commandes.get(position).getProductId()).getParent(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                //Toast.makeText(context, "Left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeTop() {
                //Toast.makeText(context, "Top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                //Toast.makeText(context, "Bottom", Toast.LENGTH_SHORT).show();
            }

            public void onLongPressItem(){
                Intent intent=new Intent(context,ProductDetailActivity.class);
                intent.putExtra("pid",list_commandes.get(position).getProductId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_commandes.size();
    }

    public Product getProduct(){
    return product;
    }

    public void setProduct(Product product){
        this.product=product;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        TextView productPrice;
        TextView productQuantity;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            productName=itemView.findViewById(R.id.cart_product_name);
            productPrice=itemView.findViewById(R.id.cart_product_price);
            productQuantity= itemView.findViewById(R.id.cart_product_quantity);

        }
    }

    public Product getProductById(String id){

        DatabaseReference mRefProduct=FirebaseDatabase.getInstance().getReference("Products");
        Product produit1=new Product();
        mRefProduct.addValueEventListener(new ValueEventListener(id,produit1));

        return product;
    }

   public class ValueEventListener implements com.google.firebase.database.ValueEventListener {

        private String id;
        private Product produit;

        public ValueEventListener(String id,Product produit){
            this.id=id;
            this.produit=produit;
        }

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot d:dataSnapshot.getChildren()){
                if(d.child("pid").getValue().equals(id)){
                    this.produit  = d.getValue(Product.class);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(context, "No data Found !", Toast.LENGTH_SHORT).show();
        }

    }

}
