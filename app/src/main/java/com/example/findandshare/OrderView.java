package com.example.findandshare;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class OrderView extends RecyclerView.ViewHolder {

    public TextView userName ,userPhone , found_location,userCity , userDateTime;

    public OrderView(@NonNull View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.owner_user_name);
        userPhone = itemView.findViewById(R.id.owner_phone_number);
        found_location = itemView.findViewById(R.id.product_location);
        userDateTime = itemView.findViewById(R.id.date_time);
        userCity = itemView.findViewById(R.id.owner_address_city);
    }

}