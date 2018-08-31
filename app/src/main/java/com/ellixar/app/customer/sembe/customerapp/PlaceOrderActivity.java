package com.ellixar.app.customer.sembe.customerapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ellixar.app.customer.sembe.customerapp.ui.Beanclass;
import com.ellixar.app.customer.sembe.customerapp.ui.ExpandableHeightGridView;
import com.ellixar.app.customer.sembe.customerapp.ui.GridviewAdapter;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceOrderActivity extends AppCompatActivity {

    private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;


    private int[] IMAGEgrid = {R.drawable.grooming7};
    private String[] ProductPackageSize = {"1 Kgs", "2 Kgs", "5 Kgs", "10 Kgs", "50 Kgs", "90 Kgs"};
    private String[] ProductName = {"Okoa", "Kadogo", "large", "Familia"};
    private String[] ProductBuyingPrice = {"100", "200", "300", "400"};
    private String[] states = new String[]{
            "Gujrat", "Jammu and Kashmir", "Kerala", "Karnataka", "Lakshadweep", "Maharashtra", "Manipur", "Mizoram",
            "Nagaland", "New Delhi", "Rajasthan", "Tami Nadu", "West Bengal"};

    public static final String MyOrderListPREFERENCES = "MyOrderListPrefs" ;


    @BindView(R.id.button_view_single_order)
    Button btnViewSingleOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        ButterKnife.bind(this);

        btnViewSingleOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToOrderListActivity();
            }
        });


        gridview = (ExpandableHeightGridView) findViewById(R.id.gridview);
        beanclassArrayList = new ArrayList<Beanclass>();

        for (int i = 0; i < 4; i++) {
        Beanclass beanclass = new Beanclass(IMAGEgrid[0], ProductPackageSize[i], ProductName[i], ProductBuyingPrice[i], states[i], "1", "22", "1");

        beanclassArrayList.add(beanclass);
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(PlaceOrderActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.order_dialog);

                //Get value of selected id and intitialize it to x
                final int x = (int) id;

                dialog.show();
            }
        });

        gridviewAdapter = new GridviewAdapter(PlaceOrderActivity.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Dialog dialog = new Dialog(PlaceOrderActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.order_dialog);

                //Get value of selected id and intitialize it to x
                final int x = (int) id;

                //Get values of selected product from database
                String product_id = "";
                product_id = "";

                String product_name = "";
                product_name = "";

                String product_selling_price = "";
                product_selling_price = "" ;

                String product_quantity = "";
                product_quantity = "" ;

                String product_discount = "";
                product_discount = "" ;



                dialog.show();


            }
        });

    }

    private void sendToOrderListActivity() {
        Intent intent = new Intent(this, ViewSingleOrderActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
