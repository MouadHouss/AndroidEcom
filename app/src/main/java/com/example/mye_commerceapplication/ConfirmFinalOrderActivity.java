package com.example.mye_commerceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mye_commerceapplication.Model.LigneCommande;
import com.example.mye_commerceapplication.Model.Order;
import com.example.mye_commerceapplication.Model.Product;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText cityEdtText ,nameEditText,phoneEditText,addressEditText;
    private Button confirmOrderBtn;
   // private String phoneSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        nameEditText=this.findViewById(R.id.shippement_name);
        cityEdtText=this.findViewById(R.id.shippement_city_name);
        phoneEditText=this.findViewById(R.id.shippement_phone_number);
        addressEditText=this.findViewById(R.id.shippement_home_address);
        confirmOrderBtn=this.findViewById(R.id.confirm_order_btn);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields();
                //affichage de texte pour le feliciter le client de ses nouveaux achats
                Intent intent=new Intent(ConfirmFinalOrderActivity.this,SalesActivity.class);
                startActivity(intent);
            }
        });


    }

    private void checkFields(){

        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this, "name is missing ...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(cityEdtText.getText().toString())){
            Toast.makeText(this, "city is missing ...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this, "adress is missing ...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this, "phone Number is missing ...", Toast.LENGTH_SHORT).show();
        }

        else {
            //Toast.makeText(this, "name is missing ...", Toast.LENGTH_SHORT).show();
            confirmOrder();
        }

    }

    private void confirmOrder() {
        final DatabaseReference mRefOrder,mRefCommande;
        mRefOrder=FirebaseDatabase.getInstance().getReference("Orders").child(Prevalent.currentOnlineUser.getPhone());

        mRefCommande=FirebaseDatabase.getInstance().getReference("ligneCommande");



        String numVendeur;

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String dateOrder=df.format(dateobj);

        final Order order=new Order(nameEditText.getText().toString(),phoneEditText.getText().toString(),addressEditText.getText().toString(),dateOrder,"not shipped",getIntent().getStringExtra("totalAmount"));

        mRefOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRefOrder.setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ConfirmFinalOrderActivity.this, "your order has been registered succesfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ConfirmFinalOrderActivity.this, "Error Data ...", Toast.LENGTH_SHORT).show();
            }
        });


        System.out.println("next operation .....");

        mRefCommande.child(Prevalent.currentOnlineUser.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    //Prevalent.ligneCommande=dataSnapshot1.getValue(LigneCommande.class);
                    final LigneCommande ligne=dataSnapshot1.getValue(LigneCommande.class);

                    //System.out.println("lignecommande trouve n: "+Prevalent.ligneCommande.getIdLignCommande());

//                    DatabaseReference mRefVentes=FirebaseDatabase.getInstance().getReference("Ventes").child("3");
//                    mRefVentes.child(Prevalent.ligneCommande.getIdLignCommande()).setValue("hello");
                    //ici ca marche !!!
                        //recuperer la ligne de commande


                    DatabaseReference mRefProduct = FirebaseDatabase.getInstance().getReference("Products");
                    mRefProduct.child(ligne.getProductId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Product p= dataSnapshot.getValue(Product.class);
                            //String s=dataSnapshot.getKey();
                            //System.out.println("le key de l'objet trouve est : "+s);
                            final String phoneSeller = p.getPhonenumber();

                            Map<String ,String> map=new HashMap<>();
                            map.put("idLignCommande",ligne.getIdLignCommande());
                            map.put("date",ligne.getDate());
                            map.put("productId",ligne.getProductId());
                            map.put("quantity",ligne.getQuantity());
                            map.put("telNumberClient",ligne.getTelNumber());
                            map.put("telNumberVendeur",phoneSeller);
                            map.put("status","not shipped");

                            DatabaseReference mRefVentes=FirebaseDatabase.getInstance().getReference("Ventes");
                            mRefVentes.child(ligne.getIdLignCommande()).setValue(map);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });


                }
                //suppression des lignes de commande !!!!...

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//newwwwwwwwwwww
        mRefCommande.child(Prevalent.currentOnlineUser.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "ligne de commande is removed sucessfully!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

//                            FirebaseDatabase.getInstance().getReference("ligneCommande")
//                                    .child(Prevalent.currentOnlineUser.getPhone())
//                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(ConfirmFinalOrderActivity.this, "your orders have been registered...", Toast.LENGTH_SHORT).show();
//                                        Intent intent=new Intent(ConfirmFinalOrderActivity.this,CartActivity.class);
//                                        startActivity(intent);
//                                    }
//                                }
//                            });



    }

//    public String getSeller(String idProduct){
//        final DatabaseReference mRefProduct;
//        mRefProduct = FirebaseDatabase.getInstance().getReference("Products").child(idProduct);
//        mRefProduct.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Product p= dataSnapshot.getValue(Product.class);
//                phoneSeller=p.getPhonenumber();
//                //return phoneSeller;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        return phoneSeller;
//    }

}
