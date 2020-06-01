package com.example.mye_commerceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mye_commerceapplication.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private String product_searched;
    private String description;
    private DatabaseReference mRef;
    private ArrayList<Product> list_products;
    private RecyclerView listView;
    //private RecyclerView.LayoutManager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        product_searched = getIntent().getStringExtra("product_description");
        list_products = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("Products");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    /*if(d.child("description")){//equals(product_searched)
                        list_products.add(d.getValue(Product.class));
                    }*/

                    String str = new String(d.child("description").toString());
                    String search = product_searched;
                    if (str.contains(search)) {//str.indexOf(search)>-1
                        Toast.makeText(SearchActivity.this, "un produit a ete trouve ...", Toast.LENGTH_SHORT).show();
                        list_products.add(d.getValue(Product.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        int i = 0;
        for (Product p : list_products) {
            Log.i("ProductList", "ProductList " + list_products.get(i));
            Log.i("ProductList", "ProductList ");
            i++;
        }
        listView = findViewById(R.id.recycler_menu_searched);
        listView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
        ProductsAdapter productsAdapter = new ProductsAdapter(SearchActivity.this, list_products);//,HomeActivity.this);
        listView.setAdapter(productsAdapter);

    }



}