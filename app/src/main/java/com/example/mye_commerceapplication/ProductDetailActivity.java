package com.example.mye_commerceapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mye_commerceapplication.Model.Comment;
import com.example.mye_commerceapplication.Model.LigneCommande;
import com.example.mye_commerceapplication.Model.Product;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {

    private String  productId="";
    private Button addToCart;
    private  ImageView product_image;
    private ElegantNumberButton numberButton;
    private TextView description_product,name_product,price_product;

    private DatabaseReference mRefComment;

    private EditText comment_text;
    private Button comment_btn;
    private TextView feedback_text;
    private TextView thanksForFeedback;

    private boolean ligneCommandeExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productId=getIntent().getStringExtra("pid");
        addToCart=this.findViewById(R.id.add_product_cart);
        product_image=this.findViewById(R.id.product_image_detail);
        numberButton=this.findViewById(R.id.number_btn);
        description_product=this.findViewById(R.id.product_description_details);
        name_product=this.findViewById(R.id.product_name_details);
        price_product=this.findViewById(R.id.product_price_details);

        comment_text=this.findViewById(R.id.detail_feedback_comment);
        comment_btn=this.findViewById(R.id.detail_feedbak_btn);
        feedback_text=this.findViewById(R.id.detail_feedback_text);
        thanksForFeedback=this.findViewById(R.id.detail_feedback_thanks);

        getProductId(productId);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToCart(productId);
            }
        });

        mRefComment=FirebaseDatabase.getInstance().getReference("comments");

        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("comment button is clicked");
                final String idCmt;
                idCmt="com_"+Prevalent.currentOnlineUser.getPhone()+"_"+productId;
                Date dateComment=new Date();
                DateFormat dateFormat=new SimpleDateFormat("dd/MM/yy HH:mm:ss");

                if(!TextUtils.isEmpty(comment_text.toString())){
                    Comment comment=new Comment(idCmt,dateFormat.format(dateComment).toString(),comment_text.getText().toString(),Prevalent.currentOnlineUser.getPhone(),productId);
                    mRefComment.child(idCmt).setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            comment_text.setVisibility(View.INVISIBLE);
                            comment_btn.setVisibility(View.INVISIBLE);
                            feedback_text.setVisibility(View.INVISIBLE);
                            thanksForFeedback.setVisibility(View.VISIBLE);
                        }
                    });
                }

                else{
                    Toast.makeText(ProductDetailActivity.this, "Feedback is empty ! please tell us your feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


        public void addProductToCart(final String productId) {

            ligneCommandeExist=false;
            final DatabaseReference mRefLigneCommande;
            mRefLigneCommande=FirebaseDatabase.getInstance().getReference();//.child("ligneCommande");

            final HashMap<String,String> hashMap=new HashMap<>();

            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            final String idLigneCommande;
            //idLigneCommande=mRefLigneCommande.child("ligneCommande").child(Prevalent.currentOnlineUser.getPhone()).push().getKey();
            idLigneCommande=Prevalent.currentOnlineUser.getPhone()+productId;
            final LigneCommande ligneCommande=new LigneCommande(idLigneCommande,df.format(dateobj),productId,numberButton.getNumber(),Prevalent.currentOnlineUser.getPhone());

            mRefLigneCommande.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    if(dataSnapshot.child("ligneCommande").child(Prevalent.currentOnlineUser.getPhone()).child(idLigneCommande).exists()){

                        mRefLigneCommande.child("ligneCommande").child(Prevalent.currentOnlineUser.getPhone()).child(idLigneCommande).child("quantity").setValue(numberButton.getNumber()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ProductDetailActivity.this, "Command Updated Successfully", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }
                    //if(ligneCommandeExist==false){
                    //Toast.makeText(ProductDetailActivity.this, "New Command", Toast.LENGTH_SHORT).show();
                    mRefLigneCommande.child("ligneCommande").child(Prevalent.currentOnlineUser.getPhone()).child(idLigneCommande).setValue(ligneCommande).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProductDetailActivity.this, "added to cart list successfully ...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                                startActivity(intent);
                            }
                        }
                    });


                    Prevalent.numberOfCommands++;
                    Prevalent.numberOfCommandText.setText(String.valueOf(Prevalent.numberOfCommands));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    private void getProductId(final String productId) {

        DatabaseReference mRef;
        mRef= FirebaseDatabase.getInstance().getReference().child("Products");

        mRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Product product=dataSnapshot.getValue(Product.class);
                    System.out.println("product detail: "+product.getPid()+" description is : "+product.getDescription());
                    description_product.setText(product.getDescription());
                    price_product.setText(product.getPrice());
                    name_product.setText(product.getPname());
                    //product_image.setImageResource(R.drawable.tshirtt);
                    Picasso.get().load(product.getImage()).into(product_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
