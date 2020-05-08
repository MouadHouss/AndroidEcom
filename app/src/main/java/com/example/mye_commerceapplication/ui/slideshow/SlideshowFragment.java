package com.example.mye_commerceapplication.ui.slideshow;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mye_commerceapplication.Model.Products;
import com.example.mye_commerceapplication.Model.ProductsFireBase;
import com.example.mye_commerceapplication.Model.Users;
import com.example.mye_commerceapplication.Model.UsersFireBase;
import com.example.mye_commerceapplication.R;
import com.example.mye_commerceapplication.SellerActivityInterface;
import com.example.mye_commerceapplication.SellerMainActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    TextView text_name_profil_seller;
    TextView text_phone_profil_seller;
    TextView text_product_profil_seller;
    TextView text_sells_profil_seller;
    Users user;
    private SellerActivityInterface mListener;
    private String phoneSeller;
    private int numProducts=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        text_name_profil_seller=root.findViewById(R.id.name_profil_seller);
        text_phone_profil_seller=root.findViewById(R.id.phone_profil_seller);
        text_product_profil_seller=root.findViewById(R.id.number_product_profil_seller);
        text_sells_profil_seller=root.findViewById(R.id.number_sells_profil_seller);

        //final String phoneNumber = this.getActivity().getIntent().getExtras().getString("phoneSeller");
        Log.i("phoneSellerViaInterface", "phoneSeller : "+ mListener.getPhoneSeller());
        phoneSeller= mListener.getPhoneSeller();

        new UsersFireBase().readUsers(new UsersFireBase.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Users> users, ArrayList<String> keys) {
                for(Users u : users){

                    if(u.getPhone().equals(mListener.getPhoneSeller())) {
                        user = u;
                    }
                }

                text_name_profil_seller.setText(user.getName());
                text_phone_profil_seller.setText(user.getPhone());
                Log.i("UserPhone", "UserPhone :"+user.getPhone());



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

        new ProductsFireBase().readProducts(new ProductsFireBase.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Products> products, ArrayList<String> keys) {
                for(Products p : products){
                    if (p.getPhonenumber().equals(mListener.getPhoneSeller())){
                        numProducts++;
                    }

                }
                text_product_profil_seller.setText(String.valueOf(numProducts));
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
}
