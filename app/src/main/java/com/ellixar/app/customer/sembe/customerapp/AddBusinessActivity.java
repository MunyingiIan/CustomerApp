package com.ellixar.app.customer.sembe.customerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBusinessActivity extends AppCompatActivity {

    @BindView(R.id.button_cancel)
    Button cancelBtn;
    @BindView(R.id.button_register_business)
    Button registerBusinessBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);

        ButterKnife.bind(this);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToAccount();
            }
        });
        registerBusinessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToAccount();
            }
        });
    }

    private void sendToAccount() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),AccountActivity.class);
        startActivity(intent);
        finish();
    }
}
