package com.example.mye_commerceapplication.ui.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mye_commerceapplication.Model.Orders;
import com.example.mye_commerceapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerView_config_Commandes {
    private Context mcontext;
    private MyAdapter_Commands mOrdersAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<Orders> orders, ArrayList<String> keys){
        mcontext=context;
        mOrdersAdapter= new MyAdapter_Commands(orders,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mOrdersAdapter);
    }

    public class MyAdapter_Commands extends RecyclerView.Adapter<ViewHolderCommands> {
        private ArrayList<Orders> ordersList;
        private ArrayList<String> mKeys;

        public MyAdapter_Commands(ArrayList<Orders> data, ArrayList<String> mKeys) {
            this.ordersList = data;
            this.mKeys = mKeys;
        }

        @Override
        public ViewHolderCommands onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commandes_seller, parent, false);

            ViewHolderCommands viewHolder = new ViewHolderCommands(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderCommands holder, int position) {
            holder.bind(ordersList.get(position),mKeys.get(position));

        }

        @Override
        public int getItemCount() {
            return ordersList.size();

        }
    }

    public class ViewHolderCommands extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewNumBuyer;
        TextView textViewAdresseBuyer;
        TextView textViewDate;
        TextView textViewPrice;
        TextView textViewStatut;



        private String key;

        public ViewHolderCommands(View itemView)
        {
            super(itemView);
            this.imageView=(ImageView) itemView.findViewById(R.id.product_image_commande);
            this.textViewNumBuyer = (TextView) itemView.findViewById(R.id.num_buyer_command);
            this.textViewAdresseBuyer=(TextView) itemView.findViewById(R.id.adresse_buyer_command);
            this.textViewDate = (TextView) itemView.findViewById(R.id.date_command);
            this.textViewPrice = (TextView) itemView.findViewById(R.id.price_command);
            this.textViewStatut = (TextView) itemView.findViewById(R.id.status_command);
        }

        public void bind(Orders order, String key){
            textViewNumBuyer.setText(order.getPhone());
            textViewAdresseBuyer.setText(order.getAddress());
            textViewDate.setText(order.getDate());
            textViewPrice.setText(order.getTotalAmount());
            textViewStatut.setText(order.getState());
            if(order.getState().equals("not shipped")){
                imageView.setImageResource(R.drawable.notdelivered);

            }
            else{
                imageView.setImageResource(R.drawable.delivered);

            }
            //Picasso.get().load(produit.getImage()).into(imageView);


            this.key=key;
        }
    }
}
