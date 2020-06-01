package com.example.mye_commerceapplication.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.mye_commerceapplication.SellerActivityInterface;
import com.example.mye_commerceapplication.SellerMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    ImageView image_input;
    Button button_add_product;
    Button button_upload;
    ProductsFireBase pfb;
    final Products produit=new Products();
    final Ajouts ajout=new Ajouts();
    final private static int ImageBack = 1;
    private StorageReference Folder;
    private SellerActivityInterface mListener;
    private String phoneSeller;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        addProductViewModel = ViewModelProviders.of(this).get(AddProductsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_product, container, false);
        final TextView textView = root.findViewById(R.id.text_add_product);
        text_input_name=root.findViewById(R.id.input_name_addproduct);
        text_input_description=root.findViewById(R.id.input_description_addproduct);
        text_input_categorie=root.findViewById(R.id.input_categorie_addproduct);
        text_input_price=root.findViewById(R.id.input_price_addproduct);
        text_input_quantity=root.findViewById(R.id.input_quantity_addproduct);
        button_add_product=root.findViewById(R.id.addproduct_btn);
        //button_upload=root.findViewById(R.id.addproduct_upload);
        image_input=root.findViewById(R.id.input_imageView_addproduct);
        addProductViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        textView.setText(s);
                    }
                }
        );
        phoneSeller= mListener.getPhoneSeller();
        Folder = FirebaseStorage.getInstance().getReference().child("images");

        new ProductsFireBase().readProducts(new ProductsFireBase.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Products> products, ArrayList<String> keys) {
                int maxID=0;
                if(products.isEmpty()){

                }
                else{
                    for (Products p : products){

                        if( Integer.parseInt(p.getPid().substring(2))   >maxID){
                            maxID=Integer.parseInt(p.getPid().substring(2));
                        }
                    }
                }


                String idProduct="id"+String.valueOf(maxID+1);
                produit.setPid(idProduct);
                ajout.setIdProduit(idProduct);

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        new AjoutsFireBase().readAjouts(new AjoutsFireBase.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Ajouts> ajouts, ArrayList<String> keys) {
                int IDAjout=0;
                if(ajouts.isEmpty()){

                }
                else{
                    for (Ajouts ajt : ajouts){

                        if(ajt.getIdAjout()>IDAjout){
                            IDAjout=ajt.getIdAjout();
                        }
                    }
                }
                IDAjout++;
                ajout.setIdAjout(IDAjout);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        button_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                produit.setPname(text_input_name.getText().toString());
                produit.setPrice(text_input_price.getText().toString());
                produit.setPhonenumber(phoneSeller);
                produit.setDescription(text_input_description.getText().toString());
                produit.setCategory(text_input_categorie.getText().toString());

                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                ajout.setQuantity(Integer.parseInt(text_input_quantity.getText().toString()));
                ajout.setDateAjout(date);
                new ProductsFireBase().addProduct(produit);
                new AjoutsFireBase().addAjout(ajout);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Navigation.findNavController(activity,R.id.nav_host_fragment).navigate(R.id.nav_gallery);

            }
        });


        image_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadData(v);
            }


        });


        return root;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof SellerActivityInterface) {
            mListener = (SellerActivityInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement YourActivityInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                try{
                    Bitmap bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),ImageData);
                    image_input.setImageBitmap(bitmap);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                final StorageReference imageName = Folder.child("image"+ImageData.getLastPathSegment());
                UploadTask uploadTask=imageName.putFile(ImageData);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

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

