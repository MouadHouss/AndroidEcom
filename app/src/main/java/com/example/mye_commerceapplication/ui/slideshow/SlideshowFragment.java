package com.example.mye_commerceapplication.ui.slideshow;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
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
import com.example.mye_commerceapplication.Model.Ventes;
import com.example.mye_commerceapplication.Model.VentesFireBase;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.example.mye_commerceapplication.R;
import com.example.mye_commerceapplication.SellerActivityInterface;
import com.example.mye_commerceapplication.SellerMainActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    TextView text_name_profil_seller;
    TextView text_phone_profil_seller;
    TextView text_product_profil_seller;
    TextView text_sells_profil_seller;
    TextView text_pass_profil_seller;
    TextView text_adress_profil_seller;
    Users user;
    private SellerActivityInterface mListener;
    private String phoneSeller;
    private int numProducts=0;
    private int numSells=0;
    Geocoder geocoder;
    private List<Address> addresses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        text_name_profil_seller=root.findViewById(R.id.name_profil_seller);
        text_phone_profil_seller=root.findViewById(R.id.phone_profil_seller);
        text_product_profil_seller=root.findViewById(R.id.number_product_profil_seller);
        text_sells_profil_seller=root.findViewById(R.id.number_sells_profil_seller);
        text_pass_profil_seller=root.findViewById(R.id.pass_phone_seller);
        text_adress_profil_seller=root.findViewById(R.id.tv_address);
        //final String phoneNumber = this.getActivity().getIntent().getExtras().getString("phoneSeller");
        Log.i("phoneSellerViaInterface", "phoneSeller : "+ mListener.getPhoneSeller());
        phoneSeller= mListener.getPhoneSeller();



        /*
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

         */
        user=Prevalent.currentOnlineUser;
        text_name_profil_seller.setText(user.getName());
        text_phone_profil_seller.setText(user.getPhone());
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.valueOf(Prevalent.currentOnlineUser.getLatitude()),Double.valueOf(Prevalent.currentOnlineUser.getLongitude()), 1);
            text_adress_profil_seller.setText(addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }


        new VentesFireBase().readVentes(new VentesFireBase.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Ventes> ventes, ArrayList<String> keys) {
                for (Ventes v:ventes){
                    if (v.getTelNumberVendeur().equals(mListener.getPhoneSeller())){
                        numSells++;
                    }
                }
                text_sells_profil_seller.setText(String.valueOf(numSells));
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
