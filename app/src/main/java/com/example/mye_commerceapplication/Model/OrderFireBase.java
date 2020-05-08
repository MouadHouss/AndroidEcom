package com.example.mye_commerceapplication.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderFireBase {

    private FirebaseDatabase mDataBase;
    private DatabaseReference mReferenceOrders;
    private ArrayList<Orders> orders = new ArrayList<>();

    public OrderFireBase(){
        mDataBase= FirebaseDatabase.getInstance();
        mReferenceOrders= mDataBase.getReference("Orders");
    }
    public interface DataStatus{
        void DataIsLoaded(ArrayList<Orders> orders, ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public void readOrders(final OrderFireBase.DataStatus dataStatus){
        mReferenceOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();
                ArrayList<String> keys= new ArrayList<>();
                for( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Log.i("keyNode", "keyNodeValue :"+ keyNode.getValue() +" - keyNodeKey :"+keyNode.getKey());
                    keys.add(keyNode.getKey());
                    Orders orders = keyNode.getValue(Orders.class);
                    OrderFireBase.this.orders.add(orders);

                }
                dataStatus.DataIsLoaded(orders,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





}