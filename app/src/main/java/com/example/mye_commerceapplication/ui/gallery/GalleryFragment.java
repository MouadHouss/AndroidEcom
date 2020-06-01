package com.example.mye_commerceapplication.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mye_commerceapplication.Model.Product;
import com.example.mye_commerceapplication.Model.Products;
import com.example.mye_commerceapplication.Model.ProductsFireBase;
import com.example.mye_commerceapplication.Model.Ventes;
import com.example.mye_commerceapplication.R;
import com.example.mye_commerceapplication.SellerActivityInterface;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private ArrayList<Products> Produits;
    private RecyclerView rv;
    private TextView mTextViewEmpty;
    private ProductsFireBase productFireBase;
    private ArrayList<Products> productsList;
    private SellerActivityInterface mListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        rv=(RecyclerView)root.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        productsList=new ArrayList<Products>();
        final String phoneSeller= mListener.getPhoneSeller();

        new ProductsFireBase().readProducts(new ProductsFireBase.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Products> products, ArrayList<String> keys) {
                for (Products p:products){
                    if (p.getPhonenumber().equals(phoneSeller)){
                        productsList.add(p);
                    }
                }
                new RecyclerView_config().setConfig(rv,GalleryFragment.this.getContext(),productsList,keys);
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

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);

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