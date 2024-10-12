package com.example.findandshare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private Button btn_logout, btn_viewAllOrders, btn_add_category;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btn_logout = (Button) findViewById(R.id.btn_logout_admin);
        btn_viewAllOrders = (Button) findViewById(R.id.btn_order_list);
        btn_add_category = (Button) findViewById(R.id.btn_add_product);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        btn_viewAllOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminOrderListActivity.class);
                startActivity(intent);
            }
        });

        btn_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminCategoryActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        alertDialogBuilder = new AlertDialog.Builder(AdminHomeActivity.this);

        alertDialogBuilder.setTitle("Exit?");

        alertDialogBuilder.setMessage("Do you want to exit?");

        alertDialogBuilder.setIcon(R.drawable.exit);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //for exiting the app;
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
