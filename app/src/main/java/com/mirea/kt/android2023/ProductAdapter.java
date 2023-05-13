package com.mirea.kt.android2023;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    Context context;
    List<Products> list;
    ProductClickListener listener;

    public ProductAdapter(Context context, List<Products> list, ProductClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        holder.product_title.setText(list.get(position).getTitle());
        holder.product_quantity.setText(list.get(position).getQuantitu());

        if (list.get(position).getIsBuy() == "1"){
            holder.isBuyPicture.setImageResource(R.drawable.ic_action_check);
        }else {
            holder.isBuyPicture.setImageResource(0);
        }

        holder.product_conteiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });


        holder.product_conteiner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()),holder.product_conteiner);
                return true;
            }
        });
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        CardView product_conteiner;
        TextView product_title, product_quantity;
        ImageView isBuyPicture;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_conteiner = itemView.findViewById(R.id.product_conteiner);
            product_title = itemView.findViewById(R.id.product_title);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            isBuyPicture = itemView.findViewById(R.id.isBuyPicture);
        }
    }

}


