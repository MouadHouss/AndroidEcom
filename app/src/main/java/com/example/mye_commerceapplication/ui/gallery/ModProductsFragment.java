package com.example.mye_commerceapplication.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.mye_commerceapplication.Model.Ajouts;
import com.example.mye_commerceapplication.Model.AjoutsFireBase;
import com.example.mye_commerceapplication.Model.Products;
import com.example.mye_commerceapplication.Model.ProductsFireBase;
import com.example.mye_commerceapplication.Model.Ventes;
import com.example.mye_commerceapplication.Model.VentesFireBase;
import com.example.mye_commerceapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ModProductsFragment extends Fragment {

    String idProduct;
    EditText text_input_name;
    EditText text_input_description;
    EditText text_input_category;
    EditText text_input_size;
    EditText text_input_quantity;
    ImageView imageView;
    EditText text_input_price;
    Button button_mod_produit;
    Button button_supp_produit;
    Products produit;
    Ajouts ajout;
    ArrayList<Products> myProductsList;
    private ProductsFireBase productFireBase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mod_product, container, false);
        text_input_name=root.findViewById(R.id.input_name_mod_product);
        text_input_description=root.findViewById(R.id.input_description_mod_product);
        text_input_category=root.findViewById(R.id.input_category_mod_product);
        text_input_price=root.findViewById(R.id.input_price_mod_product);
        text_input_quantity=root.findViewById(R.id.input_quantity_mod_product);
        imageView=root.findViewById(R.id.image_mod_product);
        button_mod_produit=root.findViewById(R.id.button_mod_mod_product);

        Bundle bundle = this.getArguments();
        idProduct = bundle.get("idProduct").toString();
        /*
        if(bundle != null){

        }
        */
        new ProductsFireBase().readProducts(new ProductsFireBase.DataStatus() {

            @Override
            public void DataIsLoaded(ArrayList<Products> products, ArrayList<String> keys) {
                for(Products p : products){

                    if(p.getPid().equals(idProduct)) {
                        produit = p;
                    }
                }

                text_input_name.setText(produit.getPname());
                text_input_description.setText(produit.getDescription());
                text_input_category.setText(produit.getCategory());
                text_input_price.setText(produit.getPrice());

                Picasso.get().load(produit.getImage()).into(imageView);

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
                for (Ajouts ajt:ajouts){
                    if (ajt.getIdProduit().equals(idProduct)){
                        ajout=ajt;


                    }
                }
                text_input_quantity.setText(String.valueOf(ajout.getQuantity()));
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


        button_mod_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produit.setPname(text_input_name.getText().toString());
                produit.setCategory(text_input_category.getText().toString());
                produit.setDescription(text_input_description.getText().toString());
                produit.setPrice(text_input_price.getText().toString());
                ajout.setQuantity(Integer.valueOf(text_input_quantity.getText().toString()));
                new ProductsFireBase().addProduct(produit);
                new AjoutsFireBase().addAjout(ajout);
                Navigation.findNavController((AppCompatActivity)v.getContext(),R.id.nav_host_fragment).navigate(R.id.nav_gallery);

            }
        });
        return root;
    }
}
