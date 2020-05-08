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

public class AjoutsFireBase {
    private FirebaseDatabase mDataBase;
    private DatabaseReference mReferenceAjouts;
    private ArrayList<Ajouts> ajouts = new ArrayList<>();

    public AjoutsFireBase(){
        mDataBase= FirebaseDatabase.getInstance();
        mReferenceAjouts= mDataBase.getReference("Ajouts");
    }
    public interface DataStatus{
        void DataIsLoaded(ArrayList<Ajouts> ajouts,ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public void readAjouts(final AjoutsFireBase.DataStatus dataStatus){
        mReferenceAjouts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ajouts.clear();
                ArrayList<String> keys= new ArrayList<>();
                for( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Log.i("keyNode", "keyNodeValue :"+ keyNode.getValue() +" - keyNodeKey :"+keyNode.getKey());
                    keys.add(keyNode.getKey());
                    Ajouts Ajout = keyNode.getValue(Ajouts.class);
                    ajouts.add(Ajout);

                }
                dataStatus.DataIsLoaded(ajouts,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public int readLastIDAjout(){
        final int[] idAjout = new int[1];
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReferenceAjouts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Ajouts ajout=keyNode.getValue(Ajouts.class);
                    idAjout[0] =ajout.getIdAjout();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return idAjout[0];
    }

    public void addAjout(final Ajouts ajout){
        mReferenceAjouts.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String,Object> ajoutDataMap=new HashMap<>();
                ajoutDataMap.put("idAjout",ajout.getIdAjout());
                ajoutDataMap.put("idProduit",ajout.getIdProduit());
                ajoutDataMap.put("dateAjout",ajout.getDateAjout());
                ajoutDataMap.put("quantity",ajout.getQuantity());
                mReferenceAjouts.child(String.valueOf(ajout.getIdAjout())).updateChildren(ajoutDataMap).addOnCompleteListener(new OnCompleteListener<Void>(){
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

