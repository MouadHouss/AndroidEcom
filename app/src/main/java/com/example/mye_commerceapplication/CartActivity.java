package com.example.mye_commerceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mye_commerceapplication.Model.LigneCommande;
import com.example.mye_commerceapplication.Model.Product;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    public RecyclerView listView;
    public TextView totalPriceText,totalPriceValue;
    public Button next;
    public DatabaseReference mCartRef,mProductRef;
    public ArrayList<LigneCommande> list_carts;
    public static ArrayList<Product> list_produits_cart_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        next=this.findViewById(R.id.next_btn);
        totalPriceText=this.findViewById(R.id.total_price_txt);
        totalPriceValue=this.findViewById(R.id.total_price_value);
        mCartRef=FirebaseDatabase.getInstance().getReference("ligneCommande").child(Prevalent.currentOnlineUser.getPhone());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("totalAmount",String.valueOf(CartAdapter.totalPrice));
                startActivity(intent);
            }
        });

        mProductRef=FirebaseDatabase.getInstance().getReference("Products");

        list_produits_cart_activity=new ArrayList<Product>();

        mProductRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    Product p=d.getValue(Product.class);
                    list_produits_cart_activity.add(p);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        list_carts=new ArrayList<>();

        mCartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    LigneCommande ligneCommande=data.getValue(LigneCommande.class);
                    list_carts.add(ligneCommande);
                }

                listView=findViewById(R.id.list_cart);
                listView.setLayoutManager(new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL,false));

                CartAdapter cartAdapter=new CartAdapter(CartActivity.this,list_carts);//,HomeActivity.this);
                listView.setAdapter(cartAdapter);

                totalPriceText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        totalPriceValue.setText(String.valueOf(CartAdapter.totalPrice));//?????doesn't woeìrking.....
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CartActivity.this, "Ooops ! error retrieving Carts ...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
