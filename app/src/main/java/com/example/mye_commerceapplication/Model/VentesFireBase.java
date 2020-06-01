package com.example.mye_commerceapplication.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class VentesFireBase {

    private FirebaseDatabase mDataBase;
    private DatabaseReference mReferenceVentes;
    private ArrayList<Ventes> ventes = new ArrayList<>();

    public VentesFireBase(){
        mDataBase= FirebaseDatabase.getInstance();
        mReferenceVentes= mDataBase.getReference("Ventes");
    }
    public interface DataStatus{
        void DataIsLoaded(ArrayList<Ventes> ventes, ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public void readVentes(final VentesFireBase.DataStatus dataStatus){
        mReferenceVentes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ventes.clear();
                ArrayList<String> keys= new ArrayList<>();
                for( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Log.i("keyNode", "keyNodeValue :"+ keyNode.getValue() +" - keyNodeKey :"+keyNode.getKey());
                    keys.add(keyNode.getKey());
                    Ventes vente = keyNode.getValue(Ventes.class);
                    ventes.add(vente);

                }
                dataStatus.DataIsLoaded(ventes,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addVente(final Ventes vente){
        mReferenceVentes.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String,Object> venteDataMap=new HashMap<>();
                venteDataMap.put("date",vente.getDate());
                venteDataMap.put("idLignCommande",vente.getIdLignCommande());
                venteDataMap.put("productId",vente.getProductId());
                venteDataMap.put("quantity",vente.getQuantity());
                venteDataMap.put("status",vente.getStatus());
                venteDataMap.put("telNumberClient",vente.getTelNumberClient());
                venteDataMap.put("telNumberVendeur",vente.getTelNumberVendeur());
                venteDataMap.put("longitude",vente.getLongitude());
                venteDataMap.put("latitude",vente.getLatitude());
                mReferenceVentes.child(vente.getIdLignCommande()).updateChildren(venteDataMap).addOnCompleteListener(new OnCompleteListener<Void>(){
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
