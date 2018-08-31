package com.ellixar.app.customer.sembe.customerapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ellixar.app.customer.sembe.customerapp.BusinessFragment;
import com.ellixar.app.customer.sembe.customerapp.UserProfileFragment;

/**
 * Created by Munyingi Ian on Thursday : 3/22/2018.
 */

public class AccountActivityPagerViewAdapter extends FragmentPagerAdapter {

    public AccountActivityPagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

       switch (position){
           case 0:
               return new UserProfileFragment();
           case 1:
               return new BusinessFragment();
       }
       return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
