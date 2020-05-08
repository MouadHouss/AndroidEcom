package com.example.mye_commerceapplication.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mye_commerceapplication.Model.Ajouts;
import com.example.mye_commerceapplication.Model.AjoutsFireBase;
import com.example.mye_commerceapplication.Model.Products;
import com.example.mye_commerceapplication.Model.ProductsFireBase;
import com.example.mye_commerceapplication.R;
import com.example.mye_commerceapplication.SellerMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.opencensus.tags.Tag;

public class AddProductsFragment extends Fragment {

    private AddProductsViewModel addProductViewModel;
    EditText text_input_name;
    EditText text_input_id;
    EditText text_input_description;
    EditText text_input_categorie;
    static EditText text_input_image;
    EditText text_input_price;
    EditText text_input_phonenumber;
    EditText text_input_quantity;
    Button button_add_product;
    Button button_upload;
    ProductsFireBase pfb;
    final Products produit=new Products();
    final Ajouts ajout=new Ajouts();
    final private static int ImageBack = 1;
    private StorageReference Folder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        addProductViewModel = ViewModelProviders.of(this).get(AddProductsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_product, container, false);
        final TextView textView = root.findViewById(R.id.text_add_product);
        text_input_id=root.findViewById(R.id.input_id_addproduct);
        text_input_name=root.findViewById(R.id.input_name_addproduct);
        text_input_description=root.findViewById(R.id.input_description_addproduct);
        text_input_image=root.findViewById(R.id.input_image_addproduct);
        text_input_categorie=root.findViewById(R.id.input_categorie_addproduct);
        text_input_price=root.findViewById(R.id.input_price_addproduct);
        text_input_phonenumber=root.findViewById(R.id.input_phonenumber_addproduct);
        text_input_quantity=root.findViewById(R.id.input_quantity_addproduct);
        button_add_product=root.findViewById(R.id.addproduct_btn);
        button_upload=root.findViewById(R.id.addproduct_upload);

        addProductViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        textView.setText(s);
                    }
                }
        );

        Folder = FirebaseStorage.getInstance().getReference().child("images");

        final int[] idAjout = {new AjoutsFireBase().readLastIDAjout()};
        button_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                produit.setPid(text_input_id.getText().toString());
                produit.setPname(text_input_name.getText().toString());
                produit.setPrice(text_input_price.getText().toString());
                produit.setPhonenumber(text_input_phonenumber.getText().toString());
                produit.setDescription(text_input_description.getText().toString());
                produit.setCategory(text_input_categorie.getText().toString());

                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                ajout.setIdAjout(++idAjout[0]);
                ajout.setQuantity(Integer.parseInt(text_input_quantity.getText().toString()));
                ajout.setIdProduit(text_input_id.getText().toString());
                ajout.setDateAjout(date);
                new ProductsFireBase().addProduct(produit);
                new AjoutsFireBase().addAjout(ajout);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Navigation.findNavController(activity,R.id.nav_host_fragment).navigate(R.id.nav_gallery);

            }
        });


        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadData(v);
            }


        });


        return root;
    }

    public void UploadData(View view){
        Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,ImageBack);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==ImageBack){
            if(resultCode== Activity.RESULT_OK){
                Uri ImageData = data.getData();

                final StorageReference imageName = Folder.child("image"+ImageData.getLastPathSegment());
                UploadTask uploadTask=imageName.putFile(ImageData);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                text_input_image.setText(String.valueOf(uri));
                                produit.setImage(String.valueOf(uri));

                            }
                        });
                    }
                });
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("test", "test3 failded.0");
                    }
                });
            }
        }
    }

}

