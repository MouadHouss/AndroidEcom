package com.example.mye_commerceapplication.Model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mye_commerceapplication.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class    ProductsFireBase {
    private FirebaseDatabase mDataBase;
    private DatabaseReference mReferenceProducts;
    private ArrayList<Products> products = new ArrayList<>();

    public ProductsFireBase(){
        mDataBase= FirebaseDatabase.getInstance();
        mReferenceProducts= mDataBase.getReference("Products");
    }
    public interface DataStatus{
        void DataIsLoaded(ArrayList<Products> products,ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public void readProducts(final DataStatus dataStatus){
        mReferenceProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                ArrayList<String> keys= new ArrayList<>();
                for( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Log.i("keyNode", "keyNodeValue :"+ keyNode.getValue() +" - keyNodeKey :"+keyNode.getKey());
                    keys.add(keyNode.getKey());
                    Products Product = keyNode.getValue(Products.class);
                    products.add(Product);

                }
                dataStatus.DataIsLoaded(products,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void addProduct(final Products produit){
        mReferenceProducts.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String,Object> productDataMap=new HashMap<>();
                productDataMap.put("pid",produit.getPid());
                productDataMap.put("price",produit.getPrice());
                productDataMap.put("pname",produit.getPname());
                productDataMap.put("description",produit.getDescription());
                productDataMap.put("phonenumber",produit.getPhonenumber());
                productDataMap.put("category",produit.getCategory());
                productDataMap.put("image",produit.getImage());
                mReferenceProducts.child(produit.getPid()).updateChildren(productDataMap).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task){

                    }

                });


            };

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

}
