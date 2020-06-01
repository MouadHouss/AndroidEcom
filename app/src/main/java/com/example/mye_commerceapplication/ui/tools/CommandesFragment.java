package com.example.mye_commerceapplication.ui.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mye_commerceapplication.Model.Users;
import com.example.mye_commerceapplication.Model.Ventes;
import com.example.mye_commerceapplication.Model.VentesFireBase;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.example.mye_commerceapplication.R;
import com.example.mye_commerceapplication.SellerActivityInterface;

import java.util.ArrayList;

public class CommandesFragment extends Fragment {


    private ArrayList<Ventes> ventes;
    private RecyclerView rv;
    private VentesFireBase ventesFireBase;
    private SellerActivityInterface mListener;
    private ArrayList<Ventes> ventesList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        rv=(RecyclerView)root.findViewById(R.id.recyclerViewCommandes);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        ventesList=new ArrayList<Ventes>();
        final String phoneSeller= mListener.getPhoneSeller();

        new VentesFireBase().readVentes(new VentesFireBase.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Ventes> ventes, ArrayList<String> keys) {
                for (Ventes v:ventes){
                    if (v.getTelNumberVendeur().equals(phoneSeller)){
                        ventesList.add(v);
                    }
                }
                new RecyclerView_config_Commandes().setConfig(rv, CommandesFragment.this.getContext(),ventesList,keys);

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