package com.example.findandshare;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView closeWindow;
    private TextView IDCard, Book, Mobile, Other;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        IDCard = (TextView) findViewById(R.id.id_card);
        Book = (TextView) findViewById(R.id.book);
        Mobile = (TextView) findViewById(R.id.mobile);
        Other = (TextView) findViewById(R.id.other);
        closeWindow = (ImageView) findViewById(R.id.close_window_icon);

        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        IDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AddNewProductActivity.class);
                intent.putExtra("category", "Lost_ID_Card");
                startActivity(intent);
            }
        });

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AddNewProductActivity.class);
                intent.putExtra("category", "Lost_Book");
                startActivity(intent);
            }
        });


        Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AddNewProductActivity.class);
                intent.putExtra("category", "Lost_Mobile");
                startActivity(intent);
            }
        });


        Other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AddNewProductActivity.class);
                intent.putExtra("category", "Lost_Other_Item");
                startActivity(intent);
            }
        });
    }
}