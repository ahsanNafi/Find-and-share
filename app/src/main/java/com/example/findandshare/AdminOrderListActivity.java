package com.example.findandshare;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminOrderListActivity extends AppCompatActivity {

    private RecyclerView orderListRView;
    private DatabaseReference waitingProductRef;
    private DatabaseReference allProductsRef;
    private ImageView closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_list);

        waitingProductRef = FirebaseDatabase.getInstance().getReference().child("Waiting Orders");
        orderListRView = findViewById(R.id.order_list_rview);
        orderListRView.setLayoutManager(new LinearLayoutManager(this));
        closeBtn = (ImageView) findViewById(R.id.close_view);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminOrderListActivity.this, AdminHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.Builder<Order>().setQuery(waitingProductRef, Order.class)
                .build();

        FirebaseRecyclerAdapter<Order, OrderView> adapter = new FirebaseRecyclerAdapter<Order, OrderView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderView holder, @SuppressLint("RecyclerView") final int position, @NonNull final Order model) {
                holder.userName.setText(model.getName());
                holder.userPhone.setText(model.getPhone());
                holder.userCity.setText(model.getAddress() + " " + model.getCity());
                holder.userDateTime.setText(model.getDate() + " " + model.getTime());
                holder.found_location.setText(model.getItemLocation());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence sequence[] = new CharSequence[]{
                                "Yes", "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminOrderListActivity.this);
                        builder.setTitle("Checking Delivery:");
                        builder.setItems(sequence, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    String pid = getRef(position).getKey();
                                    removeProduct(pid);
                                } else {
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public OrderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_layout, viewGroup, false);
                return new OrderView(view);
            }
        };

        orderListRView.setAdapter(adapter);
        adapter.startListening();
    }

    private void removeProduct(String pid) {
        waitingProductRef.child(pid).removeValue();
    }
}


