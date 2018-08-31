package com.ellixar.app.customer.sembe.customerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewSingleOrderActivity extends AppCompatActivity {


    private EditText customer_Phone;


    @BindView(R.id.btn_sell)
    TextView buttonSend;
    @BindView(R.id.btn_clear_cart)
    TextView buttonclearcart;
    @BindView(R.id.btn_add_a_business)
    TextView buttonAddBusiness;
    @BindView(R.id.customer_phone_number)
    TextView buttonCustomerPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_order);

        ButterKnife.bind(this);

        buttonAddBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToAddBusinessActivity();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void sendToAddBusinessActivity() {
        Intent intent = new Intent(getApplicationContext(), AddBusinessActivity.class);
        startActivity(intent);
        finish();
    }



}
