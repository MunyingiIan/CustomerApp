package com.ellixar.app.customer.sembe.customerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_place_order)
    Button placeOrderBtn;
    @BindView(R.id.button_view_order)
    Button viewOrderBtn;
    @BindView(R.id.button_account)
    Button accountBtn;
    @BindView(R.id.button_view_payments)
    Button viewPaymentBtn;

    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirebaseFirestore;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendToPlaceOrder() {
        Intent intent = new Intent(this, PlaceOrderActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendToViewOrder() {
        Intent intent = new Intent(this, ViewOrdersActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendToViewPayment() {
        Intent intent = new Intent(this, ViewPaymentActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendToUserAcount() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToPlaceOrder();
            }
        });

        viewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToViewOrder();
            }
        });

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToUserAcount();
            }
        });

        viewPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToViewPayment();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
