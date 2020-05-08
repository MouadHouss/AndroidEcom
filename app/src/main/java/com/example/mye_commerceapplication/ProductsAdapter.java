package com.example.mye_commerceapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mye_commerceapplication.Model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

 class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

     public Context context;
     public ArrayList<Product> list;

     public ProductsAdapter(Context context, ArrayList<Product> list){//, OnProductListener mOnProductListener) {
         this.context = context;
         this.list = list;
     }

     @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View product_view= LayoutInflater.from(context).inflate(R.layout.product_items_layout,parent,false);
        return new ViewHolder(product_view);
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        final Product product=list.get(position);

        holder.product_name.setText(product.getPname());
        holder.product_description.setText(product.getDescription());
        holder.product_price.setText(product.getPrice()+" MAD");
        //holder.product_image.setImageResource(R.drawable.tshirtt);
//set an image with Picasso
        Picasso.get().load(product.getImage()).into(holder.product_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        System.out.println("click product on :"+position+"  le pid est : "+product.getPid());//marche tres bien
        //Toast.makeText(H, "vous avez clique l'item num : "+position, Toast.LENGTH_SHORT).show();
        //text treatment here ...
        Intent intent = new Intent(context,ProductDetailActivity.class);
        intent.putExtra("pid",product.getPid());
        context.startActivity(intent);
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {// implements View.OnClickListener

        //OnProductListener onProductListener;
        TextView product_name;
        TextView product_description;
        TextView product_price;
        ImageView product_image;

        public ViewHolder(@NonNull View itemView) {//,OnProductListener onProductListener) {

            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_description = itemView.findViewById(R.id.product_description);
            product_image = itemView.findViewById(R.id.product_image);

        }
    }
}
