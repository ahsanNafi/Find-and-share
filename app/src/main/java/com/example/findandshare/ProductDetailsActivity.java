package com.example.findandshare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCard;
    private ImageView productImage, closePage;
    private TextView productFoundPlace, productDescription, productName;
    private String productID = "", states = "normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productID = getIntent().getStringExtra("pid");

        addToCard = (Button) findViewById(R.id.product_add_to_card_btn);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        productFoundPlace = (TextView) findViewById(R.id.product_found_place);
        productName = (TextView) findViewById(R.id.product_name_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        closePage = (ImageView) findViewById(R.id.cross_btn);

        closePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
            }
        });

        getProductDetails(productID);

        addToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (states == "delivered" || states == "Undelivered") {

                    Toast.makeText(ProductDetailsActivity.this, "Please wait for the confirmation of your previous request", Toast.LENGTH_LONG).show();

                }
                else {
                    addingToCard();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        customerOrder();
    }

    private void addingToCard() {

        String saveCurrentDate, saveCurrentTIme;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTIme = currentTime.format(calendar.getTime());

        final DatabaseReference historyList = FirebaseDatabase.getInstance().getReference().child("History List");

        final HashMap<String, Object> cardMap = new HashMap<>();
        cardMap.put("pid", productID);
        cardMap.put("pname", productName.getText().toString());
        cardMap.put("price", productFoundPlace.getText().toString());
        cardMap.put("date", saveCurrentDate);
        cardMap.put("time", saveCurrentTIme);

        historyList.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(productID).
                updateChildren(cardMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    historyList.child("Admin View").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(productID).
                            updateChildren(cardMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProductDetailsActivity.this, "Product product request is successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ProductDetailsActivity.this, FinalActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    private void getProductDetails(String productID) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");

        databaseReference.child(productID).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    productName.setText(product.getPname());
                    productDescription.setText(product.getDescription());
                    productFoundPlace.setText(product.getLocation());
                    Picasso.get().load(product.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void customerOrder() {
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Waiting Orders").child(Prevalent.currentOnlineUser.getPhone());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String state = dataSnapshot.child("state").getValue().toString();

                    if (state.equals("delivered")) {

                        states = "delivered";

                    } else if (state.equals("Undelivered")) {

                        states = "Undelivered";

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
