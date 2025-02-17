package com.example.findandshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CategorySpecActivity extends AppCompatActivity {

    private String category;
    private RecyclerView category_list;
    private ImageView closeButton;
    private TextView cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_spec);

        closeButton = (ImageView) findViewById(R.id.close_icon_btn);
        cat = (TextView) findViewById(R.id.category);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorySpecActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        category = getIntent().getExtras().get("category").toString();
        cat.setText(category);

        category_list = findViewById(R.id.category_list);
        category_list.setLayoutManager(new LinearLayoutManager(CategorySpecActivity.this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(reference.orderByChild("category").startAt(category).endAt(category) , Product.class).build();


        FirebaseRecyclerAdapter<Product, ProductView> adapter = new FirebaseRecyclerAdapter<Product, ProductView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductView holder, int position, @NonNull final Product model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductLocation.setText(model.getLocation());
                Picasso.get().load(model.getImage()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CategorySpecActivity.this,ProductDetailsActivity.class);
                        intent.putExtra("pid",model.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_layout, viewGroup, false);
                ProductView holder = new ProductView(view);
                return holder;
            }
        };

        category_list.setAdapter(adapter);
        adapter.startListening();
    }
}
