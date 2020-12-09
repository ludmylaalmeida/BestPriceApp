package com.example.bestpriceapp.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bestpriceapp.Interface.ItemClickListener;
import com.example.bestpriceapp.R;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productName, productPrice;
    public ImageView productImage;
    public ItemClickListener listener;


    public ProductViewHolder(View itemView) {
        super(itemView);

        productName = (TextView) itemView.findViewById(R.id.product_name);
//        productPrice = (TextView) itemView.findViewById(R.id.product_price);
//        productImage = (ImageView) itemView.findViewById(R.id.product_image);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }
}
