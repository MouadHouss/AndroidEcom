package com.example.mye_commerceapplication.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mye_commerceapplication.Model.Orders;
import com.example.mye_commerceapplication.Model.OrderFireBase;
import com.example.mye_commerceapplication.R;

import java.util.ArrayList;

public class CommandesFragment extends Fragment {


    private ArrayList<Orders> orders;
    private RecyclerView rv;
    private OrderFireBase orderFireBase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        rv=(RecyclerView)root.findViewById(R.id.recyclerViewCommandes);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        new OrderFireBase().readOrders(new OrderFireBase.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Orders> orders, ArrayList<String> keys) {
                new RecyclerView_config_Commandes().setConfig(rv, CommandesFragment.this.getContext(),orders,keys);
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
}