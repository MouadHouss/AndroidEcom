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

public class UsersFireBase {

    private FirebaseDatabase mDataBase;
    private DatabaseReference mReferenceUsers;
    private ArrayList<Users> Users = new ArrayList<>();

    public UsersFireBase(){
        mDataBase= FirebaseDatabase.getInstance();
        mReferenceUsers= mDataBase.getReference("Users");
    }
    public interface DataStatus{
        void DataIsLoaded(ArrayList<Users> users,ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public void readUsers(final UsersFireBase.DataStatus dataStatus){
        mReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users.clear();
                ArrayList<String> keys= new ArrayList<>();
                for( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Log.i("keyNode", "keyNodeValue :"+ keyNode.getValue() +" - keyNodeKey :"+keyNode.getKey());
                    keys.add(keyNode.getKey());
                    Users users = keyNode.getValue(Users.class);
                    Users.add(users);

                }
                dataStatus.DataIsLoaded(Users,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






}
