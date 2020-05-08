package com.example.mye_commerceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccount;
    private EditText userName,phoneNumber,password;
    private ProgressDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccount=this.findViewById(R.id.register_btn);
        userName=this.findViewById(R.id.register_username_input);
        phoneNumber=this.findViewById(R.id.register_phone_number_input);
        password=this.findViewById(R.id.register_password_input);

        loadingDialog=new ProgressDialog(this);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountUser();
            }


        });
    }

    public void createAccountUser() {

        String loginUser=userName.getText().toString();
        String passwordUser=password.getText().toString();
        String phoneUser=phoneNumber.getText().toString();

        if(TextUtils.isEmpty(loginUser)){
            Toast.makeText(this,"please enter your login",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(passwordUser)){
            Toast.makeText(this,"please enter your password",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(phoneUser)){
            Toast.makeText(this,"please enter your phone number",Toast.LENGTH_LONG).show();
        }
        else{
            loadingDialog.setTitle("create Account");
            loadingDialog.setMessage("please wait,We create your account");
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();

            validatePhoneNumber(loginUser,phoneUser,passwordUser);

        }
    }

    private void validatePhoneNumber(final String loginUser, final String phoneUser, final String passwordUser) {
    final DatabaseReference RootRef;
    RootRef= FirebaseDatabase.getInstance().getReference();
    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(!(dataSnapshot.child("users").child(phoneUser).exists())){

                HashMap<String,Object> userDataMap=new HashMap<>();
                userDataMap.put("phone",phoneUser);
                userDataMap.put("password",passwordUser);
                userDataMap.put("name",loginUser);
                RootRef.child("Users").child(phoneUser).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"congratulations your account has been created !",Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();

                            Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            loadingDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,"Ooops Error Network !",Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
            else{
                Toast.makeText(RegisterActivity.this,"This "+phoneUser+" Already exists",Toast.LENGTH_LONG).show();
                loadingDialog.dismiss();
                Toast.makeText(RegisterActivity.this,"Try again ",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }
}
