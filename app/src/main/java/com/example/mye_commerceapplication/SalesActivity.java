package com.example.mye_commerceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mye_commerceapplication.Model.Product;
import com.example.mye_commerceapplication.Model.Shopping;
import com.example.mye_commerceapplication.Model.Users;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SalesActivity extends AppCompatActivity {

    private DatabaseReference mProductsRef,mSalesRef,mRefVendeur;
    private RecyclerView listViewShopping;
    private ArrayList<Shopping> list_shopping;
    public static ArrayList<Product> list_produits_cart_activity;
    public static ArrayList<Users> list_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        mProductsRef= FirebaseDatabase.getInstance().getReference("Products");
        mSalesRef=FirebaseDatabase.getInstance().getReference("Ventes");
        mRefVendeur=FirebaseDatabase.getInstance().getReference("Users");

        list_produits_cart_activity=new ArrayList<Product>();
        mProductsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    Product p=d.getValue(Product.class);
                    list_produits_cart_activity.add(p);
                }
                listViewShopping=findViewById(R.id.recycler_menu_shopping);
                listViewShopping.setLayoutManager(new LinearLayoutManager(SalesActivity.this, RecyclerView.VERTICAL,false));
                SalesAdapter salesAdapter=new SalesAdapter(SalesActivity.this,list_shopping);//,HomeActivity.this);
                listViewShopping.setAdapter(salesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //recuperer l'ensemble des utilisateurs
        list_users=new ArrayList<Users>();
        mRefVendeur.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Users user=data.getValue(Users.class);
                    list_users.add(user);
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
        list_shopping=new ArrayList<Shopping>();

        mSalesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     for (DataSnapshot data:dataSnapshot.getChildren()){
                         Shopping shopping=data.getValue(Shopping.class);
                         if(shopping.getTelNumberClient().equals(Prevalent.currentOnlineUser.getPhone())){
                             list_shopping.add(shopping);
                         }
                     }

                listViewShopping=findViewById(R.id.recycler_menu_shopping);
                listViewShopping.setLayoutManager(new LinearLayoutManager(SalesActivity.this, RecyclerView.VERTICAL,false));

                SalesAdapter cartAdapter=new SalesAdapter(SalesActivity.this,list_shopping);//,HomeActivity.this);
                listViewShopping.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
