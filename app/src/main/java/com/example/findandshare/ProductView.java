package com.example.findandshare;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.findandshare.interfaces.ItemClickListner;


public class ProductView extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductLocation;
    public ImageView imageView;
    public ItemClickListner listener;


    public ProductView(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductLocation = (TextView) itemView.findViewById(R.id.product_location_input);
    }

    public void e(ItemClickListner listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, false);
    }
}