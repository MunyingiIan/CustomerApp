package com.ellixar.app.customer.sembe.customerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ellixar.app.customer.sembe.customerapp.adapter.AccountActivityPagerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.profileLabel)
    TextView tv_profile;
    @BindView(R.id.usersLabel)
    TextView tv_allUsers;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    AccountActivityPagerViewAdapter pagerViewAdapter;
    FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            sendToLogin();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ButterKnife.bind(this);

        pagerViewAdapter = new AccountActivityPagerViewAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerViewAdapter);

        mViewPager.addOnPageChangeListener(pageChangeListener);
        //caching of offscreenpages
        mViewPager.setOffscreenPageLimit(1);

        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });
        tv_allUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });
    }

    private void sendToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            changeTabs(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //@TargetApi(Build.VERSION_CODES.M)
    private void changeTabs(int position) {

        if (position == 0){
            tv_profile.setTextColor(ContextCompat.getColor(this,R.color.textTabBright));
            tv_profile.setTextSize(22);

            tv_allUsers.setTextSize(16);
            tv_allUsers.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));

        }
        if (position == 1){
            tv_profile.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
            tv_profile.setTextSize(16);

            tv_allUsers.setTextSize(22);
            tv_allUsers.setTextColor(ContextCompat.getColor(this,R.color.textTabBright));

        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
