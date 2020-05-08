package com.example.mye_commerceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mye_commerceapplication.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText loginEdit;
    private EditText emailEdit;
    private TextView sentText;
    private Button send;
    SendMail sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        loginEdit = this.findViewById(R.id.login_forget_input);
        emailEdit = this.findViewById(R.id.email_forget_input);
        send = this.findViewById(R.id.forget_send_btn);
        sentText=this.findViewById(R.id.forget_sent_text);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(loginEdit.getText().toString()) || TextUtils.isEmpty(emailEdit.getText().toString())) {
                    Toast.makeText(ForgetPasswordActivity.this, "login or email are empty...", Toast.LENGTH_SHORT).show();
                } else {
                    //on recupere le password du login correspondant
                    DatabaseReference mUsersRef;
                    mUsersRef = FirebaseDatabase.getInstance().getReference("Users").child(loginEdit.getText().toString());

                    mUsersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //System.out.println("login saisie est: "+loginEdit.toString());
                            if (dataSnapshot.exists()) {
                                Users usersData = dataSnapshot.getValue(Users.class);

                                //Sending the message to Mail
                                String message = "Hello\t" + usersData.getName() + ",\n" +
                                        "your Login:\t\t\t" + usersData.getPhone() + "\nyour Password:\t\t\t" + usersData.getPassword() + "\nCordially.";

                                System.out.println("password is : " + usersData.getPassword());
                                 //sm = new SendMail(ForgetPasswordActivity.this, emailEdit.getText().toString(), "Recovering my password", message);

                                //Executing sendmail to send email
                                //sm.execute();
                                sentText.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this, "Oops the Login does not exist in the database... please try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ForgetPasswordActivity.this, "Database Error...", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        //sm.getProgressDialog().dismiss();
    }
}