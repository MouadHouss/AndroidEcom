package com.example.mye_commerceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import com.rey.material.widget.CheckBox;

import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.mye_commerceapplication.Model.Users;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private TextView linkForget;
    private Button loginButton;
    private EditText phoneNumber,password;
    private ProgressDialog loadingDialog;
    private CheckBox chkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumber=this.findViewById(R.id.login_phone_number_input);
        password=this.findViewById(R.id.login_password_input);

        linkForget=this.findViewById(R.id.forget_password_link);
        loginButton=this.findViewById(R.id.login_btn);

        chkBoxRememberMe=this.findViewById(R.id.remember_me_chkb);

        Paper.init(this);

        loadingDialog=new ProgressDialog(this);

        linkForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser(){
        String phoneUser=phoneNumber.getText().toString();
        String passwordUser=password.getText().toString();

        if(TextUtils.isEmpty(phoneUser)){
            Toast.makeText(this,"please enter your phone number",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(passwordUser)){
            Toast.makeText(this,"please enter your password",Toast.LENGTH_LONG).show();
        }
        else{

            loadingDialog.setTitle("create Account");
            loadingDialog.setMessage("please wait,We access you to your account");
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
            allowAccessToAccount(phoneUser,passwordUser);
        }

    }

    private void allowAccessToAccount(final String _phone,final String _password){

        if(chkBoxRememberMe.isChecked()){

            Paper.book().write(Prevalent.UserPhoneKey, _phone);
            Paper.book().write(Prevalent.UserPasswordKey, _password);
            //sauvegarde des informations de user actuel
        }

        DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(_phone).exists()){
                    Users usersData=dataSnapshot.child("Users").child(_phone).getValue(Users.class);
                    //retrieve the informations of current user connected

                    if(usersData.getPhone().equals(_phone) && usersData.getPassword().equals(_password)){
                        Toast.makeText(LoginActivity.this,"Logged Successfully ...",Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                        //Toast.makeText(LoginActivity.this,"please enter your login",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                        Prevalent.currentOnlineUser = usersData;
                        Prevalent.phoneNumber=usersData.getPhone();
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"the phone number or password is wrong ... please try again",Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }

                }

                else {
                    Toast.makeText(LoginActivity.this,"Account does not exist ! please make sur you have an account",Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                    Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
